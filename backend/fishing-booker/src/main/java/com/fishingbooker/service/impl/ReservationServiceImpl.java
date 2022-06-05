package com.fishingbooker.service.impl;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.EventDTO;
import com.fishingbooker.dto.FreeTermDTO;
import com.fishingbooker.dto.statistics.FinanceDTO;
import com.fishingbooker.dto.statistics.ReservationNumDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.*;
import com.fishingbooker.service.PenaltyService;
import com.fishingbooker.service.PointsService;
import com.fishingbooker.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fishingbooker.model.ReservationType.ADVENTURE;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PointsService pointsService;
    @Autowired
    private RentableRepository rentableRepository;

    @Autowired
    private FreeTermRepository freeTermRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PenaltyService penaltyService;

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    private ZoneId zoneId = ZoneId.systemDefault();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Rentable> getFreeRentables(CustomerReservationDTO reservationDTO) {
        List<FreeTerm> freeTerms = this.freeTermRepository.getFreeTermsByType(reservationDTO.getType());
        Map<Rentable, FreeTerm> freeRentables = new HashMap<>();
        for (FreeTerm term : freeTerms) {
            if (!isBetweenDates(reservationDTO.getStart(), term.getStartTime(), term.getEndTime())
                    || !isBetweenDates(reservationDTO.getEnd(), term.getStartTime(), term.getEndTime())) continue;
            if (term.getType() == ReservationType.COTTAGE)
                freeRentables.put(rentableRepository.getCottageByNameAndOwner(term.getEntityName(), term.getOwnerUsername()), term);
            else if (term.getType() == ReservationType.BOAT)
                freeRentables.put(rentableRepository.getBoatByNameAndOwner(term.getEntityName(), term.getOwnerUsername()), term);
            else if (term.getType() == ADVENTURE){
                List<Rentable> adventures = rentableRepository.getAdventuresByOwner(term.getOwnerUsername());
                for(Rentable a: adventures){
                    freeRentables.put(a, term);
                }
            }
        }
        Iterator<Map.Entry<Rentable, FreeTerm>> iterator = freeRentables.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Rentable, FreeTerm> rentableFreeTermMap = iterator.next();
            String rentableName = rentableFreeTermMap.getKey().getName();
            FreeTerm term = rentableFreeTermMap.getValue();
            String ownerUsername = term.getOwnerUsername();
            if (term.getType() != ADVENTURE) {
                if (reservationRepository.getOccupied(rentableName, ownerUsername, reservationDTO.getStart(), reservationDTO.getEnd()) != null)
                    iterator.remove();
            }
            else if (reservationRepository.getOccupiedAdventure(ownerUsername, reservationDTO.getStart(), reservationDTO.getEnd()) != null)
                iterator.remove();
        }
        return List.copyOf(freeRentables.keySet());
    }

    @Override
    public FreeTerm createFreeTerm(FreeTerm freeTerm) {
        List<FreeTerm> freeTerms = new ArrayList<>();
        if(freeTerm.getType() == ADVENTURE)
            freeTerms = this.freeTermRepository.getFreeTermsByUsername(freeTerm.getOwnerUsername());
        else
            freeTerms = this.freeTermRepository.getFreeTermsByNameAndUsername(freeTerm.getEntityName(), freeTerm.getOwnerUsername());

        if (freeTerms.size() == 0) {
            this.freeTermRepository.save(freeTerm);
            return freeTerm;
        }

        while (true) {
            int numberOfEntities = freeTerms.size();
            Iterator<FreeTerm> iterator = freeTerms.iterator();
            while (iterator.hasNext()) {
                FreeTerm term = iterator.next();
                if (!hasOverlap(freeTerm, term) && !hasSequence(freeTerm, term)) continue;
                freeTerm.setStartTime(getEarlierDate(freeTerm, term));
                freeTerm.setEndTime(getLaterDate(freeTerm, term));
                this.freeTermRepository.delete(term);
                iterator.remove();
            }
            if (freeTerms.size() == numberOfEntities) break;
        }

        this.freeTermRepository.save(freeTerm);
        return freeTerm;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Reservation reserveRentable(Long rentableId, Reservation reservation) {
        try {
            if(penaltyService.countPenaltiesByCustomerUsername(reservation.getCustomerUsername()) >= 3)
                return null;
            CustomerReservationDTO reservationDTO = new CustomerReservationDTO(reservation.getType(), reservation.getStartTime(), reservation.getEndTime(), reservation.getGuests());
            Rentable rentable = null;
            switch (reservation.getType()) {
                case COTTAGE: {
                    rentable = rentableRepository.getCottageLock(rentableId);
                    break;
                }
                case BOAT: {
                    rentable = rentableRepository.getBoatLock(rentableId);
                    break;
                }
                case ADVENTURE: {
                    rentable = rentableRepository.getAdventureLock(rentableId);
                    break;
                }
            }
            if(rentable == null || !new HashSet<>(getFreeRentables(reservationDTO)).contains(rentable)) return null;
            reservation.setPrice(reservation.getPrice() - reservation.getPrice() * checkForCustomerSale(reservation)/100);
            reservationRepository.save(reservation);
            updatePoints(reservation);
            return reservation;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePoints(Reservation reservation){
        UserPoints customerPoints = pointsService.getUserPoints(reservation.getCustomerUsername());
        UserPoints ownerPoints = pointsService.getUserPoints(reservation.getOwnerUsername());
        List<Points> points = pointsService.getPoints();
        if(customerPoints == null || ownerPoints == null) return;
        customerPoints.setPoints(customerPoints.getPoints() + points.get(0).getCustomerPoints());
        ownerPoints.setPoints(ownerPoints.getPoints() + points.get(0).getOwnerPoints());

        pointsService.updateUserPoints(customerPoints);
        pointsService.updateUserPoints(ownerPoints);
    }

    public int checkForCustomerSale(Reservation reservation){
        UserPoints customerPoints = pointsService.getUserPoints(reservation.getCustomerUsername());
        List<Points> points = pointsService.getPoints();
        if(points.size() == 0) return 0;
        if(customerPoints.getPoints() >= points.get(0).getSilver() && customerPoints.getPoints() < points.get(0).getGold()) return 10;
        if(customerPoints.getPoints() >= points.get(0).getGold()) return 20;
        return 0;
    }

    @Override
    public Reservation createAction(Long rentableId, Reservation reservation) {
        CustomerReservationDTO reservationDTO = new CustomerReservationDTO(reservation.getType(), reservation.getStartTime(), reservation.getEndTime(), reservation.getGuests());
        Rentable rentable = rentableRepository.getRentableById(rentableId);
        if(rentable == null || !new HashSet<>(getFreeRentables(reservationDTO)).contains(rentable)) return null;
        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public List<Reservation> getFinishedReservations(ReservationType type, String username, boolean isCustomer) {
        if (isCustomer) return reservationRepository.getFinishedCustomerReservations(type, username);
        return reservationRepository.getFinishedOwnerReservations(username);
    }

    @Override
    public List<Reservation> getReservations(ReservationType type, String username, boolean isCustomer) {
        if (isCustomer) return reservationRepository.getCustomerReservations(type, username);
        return reservationRepository.getOwnerReservations(username);

    }
    
    @Override    
    public List<Reservation> getActions(String name, String ownerUsername){
        return reservationRepository.getActions(name, ownerUsername);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void reserveAction(Long id, String customerUsername){
        try {
            Reservation reservation = reservationRepository.getReservationById(id);
            reservation.setCustomerUsername(customerUsername);
            reservation.setPrice(reservation.getPrice() - reservation.getPrice() * checkForCustomerSale(reservation)/100);
            updatePoints(reservation);
            reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReservationNumDTO> getNumberOfReservations(String username) {
        List<ReservationNumDTO> list = new ArrayList<>();
        List<Rentable> rentables = new ArrayList<>();
        RegisteredUser user = registeredUserRepository.getUserByUsername(username);

        switch(user.getRole().getAuthority()){
            case "COTTAGE_OWNER":
                rentables = cottageRepository.getRentablesByOwnerUsername(username);
                break;
            case "BOAT_OWNER":
                rentables = boatRepository.getRentablesByOwnerUsername(username);
                break;
            case "INSTRUCTOR":
                rentables = adventureRepository.getRentablesByOwnerUsername(username);
                break;
            case "ADMIN":
                rentables = rentableRepository.findAll();
                break;
        }
        for(Rentable r : rentables){
            list.add(new ReservationNumDTO(r.getName(), reservationRepository.getNumOfReservationsByName(r.getName(), LocalDateTime .now().minusWeeks(1)),
                    reservationRepository.getNumOfReservationsByName(r.getName(), LocalDateTime .now().minusMonths(1)),
                    reservationRepository.getNumOfReservationsByName(r.getName(), LocalDateTime .now().minusYears(1))));
        }
        return list;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Cottage getCottageById(Long id){
        try {
            logger.info("Getting cottage with id: " + id);
            Cottage cottage = rentableRepository.getCottageLock(id);
            logger.info("Cottage with id: " + id + " got");
            return cottage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FinanceDTO> getFinances(String username, LocalDateTime start, LocalDateTime end) {
        List<FinanceDTO> finances = new ArrayList<>();
        RegisteredUser user = registeredUserRepository.getUserByUsername(username);
        List<Reservation> reservations = new ArrayList<>();
        if (user.getRole().getAuthority().equals("ADMIN")){
            reservations = reservationRepository.findAll();
        }
        else {
            reservations = reservationRepository.getReservationsByOwnerUsername(username);
        }
        for(Reservation r : reservations){
            if(r.getStartTime().isAfter(start) && r.getStartTime().isBefore(end)){
                finances.add(new FinanceDTO(r.getName(), calculatePrice(r.getPrice(), user.getRole().getAuthority(), r)));
            }
        }
        //finances sum earning with same name
        for(int i = 0; i < finances.size(); i++){
            for(int j = i + 1; j < finances.size(); j++){
                if(finances.get(i).getRentableName().equals(finances.get(j).getRentableName())){
                    finances.get(i).setEarning(finances.get(i).getEarning() + finances.get(j).getEarning());
                    finances.remove(j);
                    j--;
                }
            }
        }
        return finances;
    }

    private double calculatePrice(double price, String role, Reservation reservation){
        List<Points> points = pointsService.getPoints();
        if(role.equals("ADMIN"))
            return price * points.get(0).getSystemTax()/100;
        return price - price * (points.get(0).getSystemTax() - checkForOwnerBonus(reservation))/100;
    }

    public int checkForOwnerBonus(Reservation reservation){
        UserPoints ownerPoints = pointsService.getUserPoints(reservation.getOwnerUsername());
        List<Points> points = pointsService.getPoints();
        if(ownerPoints.getPoints() >= points.get(0).getSilver() && ownerPoints.getPoints() < points.get(0).getGold()) return 3;
        if(ownerPoints.getPoints() >= points.get(0).getGold()) return 5;
        return 0;
    }

    private boolean hasSequence(FreeTerm freeTerm, FreeTerm term) {
        return isSequence(freeTerm.getStartTime(), term.getEndTime()) ||
                isSequence(freeTerm.getEndTime(), term.getStartTime()) ||
                isSequence(term.getStartTime(), freeTerm.getEndTime()) ||
                isSequence(term.getEndTime(), freeTerm.getStartTime());
    }

    private boolean isSequence(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.plusDays(1).atZone(zoneId).toEpochSecond() == endTime.atZone(zoneId).toEpochSecond();
    }


    private boolean hasOverlap(FreeTerm freeTerm, FreeTerm term) {
        return isBetweenDates(freeTerm.getStartTime(), term) ||
                isBetweenDates(freeTerm.getEndTime(), term) ||
                isBetweenDates(term.getStartTime(), freeTerm) ||
                isBetweenDates(term.getEndTime(), freeTerm);
    }

    private LocalDateTime getLaterDate(FreeTerm freeTerm, FreeTerm term) {
        if (freeTerm.getEndTime().atZone(zoneId).toEpochSecond() >= term.getEndTime().atZone(zoneId).toEpochSecond())
            return freeTerm.getEndTime();
        else
            return term.getEndTime();
    }

    private LocalDateTime getEarlierDate(FreeTerm freeTerm, FreeTerm term) {
        if (freeTerm.getStartTime().atZone(zoneId).toEpochSecond() <= term.getStartTime().atZone(zoneId).toEpochSecond())
            return freeTerm.getStartTime();
        else
            return term.getStartTime();
    }

    private boolean isBetweenDates(LocalDateTime dateOfInterest, FreeTerm term) {
        return dateOfInterest.atZone(zoneId).toEpochSecond() >= term.getStartTime().atZone(zoneId).toEpochSecond()
                && dateOfInterest.atZone(zoneId).toEpochSecond() <= term.getEndTime().atZone(zoneId).toEpochSecond();
    }

    @Override
    public List<FreeTerm> getFreeTerms(EventDTO event) {
        if(event.getType() == ADVENTURE)
            return this.freeTermRepository.getFreeTermsByUsername(event.getUsername());
        return this.freeTermRepository.getFreeTermsByNameAndUsername(event.getRentableName(), event.getUsername());
    }

    @Override
    public List<Reservation> getAllReservations(EventDTO event) {
        if(event.getType() == ADVENTURE)
            return this.reservationRepository.getAllByOwnerUsername(event.getUsername());
        return this.reservationRepository.getAllByNameAndUsername(event.getRentableName(), event.getUsername());
    }

    @Override
    public void addReview(Review review) {
        Reservation reservation = this.reservationRepository.getById(review.getReservationId());
        reservation.setReviewed(true);
        this.reservationRepository.save(reservation);
        this.reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviews() {
        List<Review> reviews = this.reviewRepository.findAll();
        List<Review> reviewsToReturn = new ArrayList<>();
        for(Review review : reviews) {
            if (!review.isPublic())
                reviewsToReturn.add(review);
        }
        return reviewsToReturn;
    }

    @Override
    public void updateReview(Review review) {
        Optional<Review> reviewToUpdate = this.reviewRepository.findById(review.getReservationId());
        reviewToUpdate.orElseThrow().setApproved(review.isApproved());
        reviewToUpdate.orElseThrow().setPublic(review.isPublic());
        this.reviewRepository.save(reviewToUpdate.orElseThrow());
        if(!review.isPublic()) return;
        double avgRating = this.reviewRepository.getAverageRatingByNameAndOwner(review.getRentableName(), review.getOwnerUsername());
        int timesRated = this.reviewRepository.getNumberOfReviewsByNameAndOwner(review.getRentableName(), review.getOwnerUsername());
        double avgOwnerRating = this.reviewRepository.getOwnerAverageRating(review.getOwnerUsername());
        int timesOwnerRated = this.reviewRepository.getNumberOfOwnerReviews(review.getOwnerUsername());
        RegisteredUser owner = this.registeredUserRepository.findByUsername(review.getOwnerUsername());
        owner.setAverageRating(avgOwnerRating);
        owner.setTimesRated(timesOwnerRated);
        this.registeredUserRepository.save(owner);
        switch (review.getReservationType()) {
            case ADVENTURE: {
                Adventure adventure = this.adventureRepository.getAdventureByNameAndOwnerUsername(review.getRentableName(), review.getOwnerUsername());
                adventure.setAverageRating(avgRating);
                adventure.setTimesRated(timesRated);
                this.adventureRepository.save(adventure);
                break;
            }
            case COTTAGE: {
                Cottage cottage = this.cottageRepository.getCottageByNameAndOwnerUsername(review.getRentableName(), review.getOwnerUsername());
                cottage.setAverageRating(avgRating);
                cottage.setTimesRated(timesRated);
                this.cottageRepository.save(cottage);
                break;
            }
            case BOAT: {
                Boat boat = this.boatRepository.getBoatByNameAndOwnerUsername(review.getRentableName(), review.getOwnerUsername());
                boat.setAverageRating(avgRating);;
                boat.setTimesRated(timesRated);
                this.boatRepository.save(boat);
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public List<FreeTerm> createDayOff(FreeTermDTO dto) {
        List<FreeTerm> freeTerms;
        List<Reservation> reservations;
        if(dto.getType() == ADVENTURE){
            freeTerms = this.freeTermRepository.getFreeTermsByUsername(dto.getUsername());
            reservations = this.reservationRepository.getAllByOwnerUsername(dto.getUsername());
        }
        else{
            reservations = this.reservationRepository.getAllByNameAndUsername(dto.getRentableName(), dto.getUsername());
            freeTerms = this.freeTermRepository.getFreeTermsByNameAndUsername(dto.getRentableName(), dto.getUsername());
        }

        if (freeTerms.size() == 0) {
            return freeTerms;
        }

        if(reservations.size() != 0){
            for(Reservation reservation : reservations){
                if(dto.getStart().getDayOfYear() >= reservation.getStartTime().getDayOfYear()
                        && dto.getStart().getDayOfYear() < reservation.getEndTime().getDayOfYear()){
                    return new ArrayList<FreeTerm>();
                }
            }
        }

        for(FreeTerm freeTerm: freeTerms) {
            if(isBetweenDates(dto.getStart(), freeTerm)){
                this.freeTermRepository.deleteById(freeTerm.getId());
                FreeTerm earlierFreeTerm =
                        new FreeTerm(null,
                                freeTerm.getType(),
                                freeTerm.getEntityName(),
                                freeTerm.getOwnerUsername(),
                                freeTerm.getStartTime(),
                                dto.getStart()
                        );
                if(!freeTerm.getStartTime().equals(dto.getStart())){
                    this.freeTermRepository.save(earlierFreeTerm);
                }
                FreeTerm laterFreeTerm =
                        new FreeTerm(null,
                                freeTerm.getType(),
                                freeTerm.getEntityName(),
                                freeTerm.getOwnerUsername(),
                                dto.getStart().plusDays(1),
                                freeTerm.getEndTime()
                        );
                if(!freeTerm.getEndTime().equals(dto.getStart().plusDays(1))){
                    this.freeTermRepository.save(laterFreeTerm);
                }
            }
        }
        if(dto.getType() == ADVENTURE)
            return this.freeTermRepository.getFreeTermsByUsername(dto.getUsername());
        else
            return this.freeTermRepository.getFreeTermsByNameAndUsername(dto.getRentableName(), dto.getUsername());
    }

    @Override
    public void cancelReservation(Long reservationId) {
        Reservation reservation = this.reservationRepository.getById(reservationId);
        if(reservation.isCancelled() || reservation.getStartTime().isBefore(LocalDateTime.now().plusDays(3))){
            throw new IllegalArgumentException("Reservation is already cancelled or is too late to cancel");
        }
        reservation.setCancelled(true);
        this.reservationRepository.save(reservation);
    }

    //is date between or is equal to start or end
    private boolean isBetweenDates(LocalDateTime dateOfInterest, LocalDateTime start, LocalDateTime end) {
        return dateOfInterest.atZone(zoneId).toEpochSecond() >= start.atZone(zoneId).toEpochSecond()
                && dateOfInterest.atZone(zoneId).toEpochSecond() <= end.atZone(zoneId).toEpochSecond();
    }
}
