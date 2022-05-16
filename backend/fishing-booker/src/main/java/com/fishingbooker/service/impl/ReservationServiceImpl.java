package com.fishingbooker.service.impl;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.EventDTO;
import com.fishingbooker.dto.FreeTermDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.FreeTermRepository;
import com.fishingbooker.repository.RentableRepository;
import com.fishingbooker.repository.ReservationRepository;
import com.fishingbooker.repository.ReviewRepository;
import com.fishingbooker.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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

    private ZoneId zoneId = ZoneId.systemDefault();

    @Override
    public List<Rentable> getFreeRentables(CustomerReservationDTO reservationDTO) {
        List<FreeTerm> freeTerms = this.freeTermRepository.getFreeTermsByType(reservationDTO.getType());
        Map<Rentable, FreeTerm> freeRentables = new HashMap<>();
        for (FreeTerm term : freeTerms) {
            if (!term.getStartTime().isBefore(reservationDTO.getStart())
                    || !term.getEndTime().isAfter(reservationDTO.getEnd())) continue;
            if (term.getType() == ReservationType.COTTAGE)
                freeRentables.put(rentableRepository.getCottageByNameAndOwner(term.getEntityName(), term.getOwnerUsername()), term);
            else if (term.getType() == ReservationType.BOAT)
                freeRentables.put(rentableRepository.getBoatByNameAndOwner(term.getEntityName(), term.getOwnerUsername()), term);
            else if (term.getType() == ReservationType.ADVENTURE){
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
            if (term.getType() != ReservationType.ADVENTURE) {
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
        if(freeTerm.getType() == ReservationType.ADVENTURE)
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
        if(event.getType() == ReservationType.ADVENTURE)
            return this.freeTermRepository.getFreeTermsByUsername(event.getUsername());
        return this.freeTermRepository.getFreeTermsByNameAndUsername(event.getRentableName(), event.getUsername());
    }

    @Override
    public List<Reservation> getAllReservations(EventDTO event) {
        if(event.getType() == ReservationType.ADVENTURE)
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
    public List<FreeTerm> createDayOff(FreeTermDTO dto) {
        List<FreeTerm> freeTerms;
        List<Reservation> reservations;
        if(dto.getType() == ReservationType.ADVENTURE){
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
        if(dto.getType() == ReservationType.ADVENTURE)
            return this.freeTermRepository.getFreeTermsByUsername(dto.getUsername());
        else
            return this.freeTermRepository.getFreeTermsByNameAndUsername(dto.getRentableName(), dto.getUsername());
    }
}
