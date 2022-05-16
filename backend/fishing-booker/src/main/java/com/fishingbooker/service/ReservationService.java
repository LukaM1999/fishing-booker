package com.fishingbooker.service;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.EventDTO;
import com.fishingbooker.dto.FreeTermDTO;
import com.fishingbooker.model.FreeTerm;
import com.fishingbooker.model.Rentable;
import com.fishingbooker.model.Reservation;
import com.fishingbooker.model.ReservationType;
import com.fishingbooker.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReservationService {
    List<Rentable> getFreeRentables(CustomerReservationDTO reservationDTO);

    FreeTerm createFreeTerm(FreeTerm freeTerm);

    Reservation reserveRentable(Long rentableId, Reservation reservation);

    List<Reservation> getFinishedReservations(ReservationType type, String username, boolean isCustomer);

    List<FreeTerm> getFreeTerms(EventDTO event);

    List<Reservation> getAllReservations(EventDTO event);

    void addReview(Review review);

    List<FreeTerm> createDayOff(FreeTermDTO dto);
}
