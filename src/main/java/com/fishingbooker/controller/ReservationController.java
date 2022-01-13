package com.fishingbooker.controller;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.model.FreeTerm;
import com.fishingbooker.model.Rentable;
import com.fishingbooker.model.Reservation;
import com.fishingbooker.model.ReservationType;
import com.fishingbooker.repository.RentableRepository;
import com.fishingbooker.repository.ReservationRepository;
import com.fishingbooker.service.ReservationService;
import com.fishingbooker.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/getFreeRentables")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public List<Rentable> getFreeRentables(@RequestBody CustomerReservationDTO reservationDTO){
        return reservationService.getFreeRentables(reservationDTO);
    }

    @PostMapping("/createFreeTerm")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public FreeTerm createFreeTerm(@RequestBody FreeTerm freeTerm){
        return reservationService.createFreeTerm(freeTerm);
    }

    @PostMapping("/reserveRentable/{rentableId}")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public Reservation reserveRentable(@PathVariable Long rentableId, @RequestBody Reservation reservation){
        return reservationService.reserveRentable(rentableId, reservation);
    }
}
