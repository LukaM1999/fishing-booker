package com.fishingbooker.service;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.model.FreeTerm;
import com.fishingbooker.model.Rentable;
import com.fishingbooker.model.ReservationType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReservationService {
    List<Rentable> getFreeRentables(CustomerReservationDTO reservationDTO);

    FreeTerm createFreeTerm(FreeTerm freeTerm);
}