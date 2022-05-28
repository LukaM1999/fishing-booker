package com.fishingbooker.service.impl;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.EventDTO;
import com.fishingbooker.dto.FreeTermDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.*;
import com.fishingbooker.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

import static com.fishingbooker.model.ReservationType.ADVENTURE;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RentableRepository rentableRepository;

    @Autowired
    private FreeTermRepository freeTermRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private BoatRepository boatRepository;

    private ZoneId zoneId = ZoneId.systemDefault();

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

    @Override
    public Reservation reserveRentable(Long rentableId, Reservation reservation) {
        if(penaltyRepository.countPenaltiesByCustomerUsername(reservation.getCustomerUsername()) >= 3)
            return null;
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

    @Override
    public void reserveAction(Long id, String customerUserrname){
        Reservation reservation = reservationRepository.getReservationById(id);
        reservation.setCustomerUsername(customerUserrname);
        reservationRepository.save(reservation);
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
        double avgRating = this.reviewRepository.getAverageRatingByNameAndOwner(reservation.getName(), reservation.getOwnerUsername());
        int timesRated = this.reviewRepository.getNumberOfReviewsByNameAndOwner(reservation.getName(), reservation.getOwnerUsername());
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
                        && dto.getStart().getDayOfYear() <= reservation.getEndTime().getDayOfYear()){
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
        reservation.setCancelled(true);
        this.reservationRepository.save(reservation);
    }

    //is date between or is equal to start or end
    private boolean isBetweenDates(LocalDateTime dateOfInterest, LocalDateTime start, LocalDateTime end) {
        return dateOfInterest.atZone(zoneId).toEpochSecond() >= start.atZone(zoneId).toEpochSecond()
                && dateOfInterest.atZone(zoneId).toEpochSecond() <= end.atZone(zoneId).toEpochSecond();
    }
}
