package com.fishingbooker.service;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.EventDTO;
import com.fishingbooker.dto.FreeTermDTO;
import com.fishingbooker.dto.statistics.FinanceDTO;
import com.fishingbooker.dto.statistics.ReservationNumDTO;
import com.fishingbooker.model.FreeTerm;
import com.fishingbooker.model.Rentable;
import com.fishingbooker.model.Reservation;
import com.fishingbooker.model.ReservationType;
import com.fishingbooker.model.Review;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    List<Rentable> getFreeRentables(CustomerReservationDTO reservationDTO);

    FreeTerm createFreeTerm(FreeTerm freeTerm);

    Reservation reserveRentable(Long rentableId, Reservation reservation);

    List<Reservation> getFinishedReservations(ReservationType type, String username, boolean isCustomer);

    List<Reservation> getReservations(ReservationType type, String username, boolean isCustomer);

    List<FreeTerm> getFreeTerms(EventDTO event);

    List<Reservation> getAllReservations(EventDTO event);

    void addReview(Review review);

    List<Review> getReviews();

    void updateReview(Review review);

    List<FreeTerm> createDayOff(FreeTermDTO dto);

    void cancelReservation(Long reservationId);
    
    List<Reservation> getActions(String name, String ownerUsername);

    void reserveAction(Long id, String customerUsername);

    Reservation createAction(Long rentableId, Reservation reservation);

    List<ReservationNumDTO> getNumberOfReservations(String username);

    List<FinanceDTO> getFinances(String username, LocalDateTime startTime, LocalDateTime endTime);
}
