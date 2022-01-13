package com.fishingbooker.service.impl;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.FreeTermRepository;
import com.fishingbooker.repository.RentableRepository;
import com.fishingbooker.repository.ReservationRepository;
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
        List<FreeTerm> freeTerms = this.freeTermRepository.getFreeTermsByNameAndUsername(freeTerm.getEntityName(), freeTerm.getOwnerUsername());

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
}
