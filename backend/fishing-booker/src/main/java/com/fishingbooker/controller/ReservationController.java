package com.fishingbooker.controller;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.ReservationHistoryDTO;
import com.fishingbooker.model.*;
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

    @PostMapping("/getFinishedReservations")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public List<Reservation> getFinishedReservations(@RequestBody ReservationHistoryDTO historyDto){
        return reservationService.getFinishedReservations(historyDto.getType(), historyDto.getUsername(), historyDto.getIsCustomer());
    }

    @GetMapping("/getFreeTerms/{username}")
    public List<FreeTerm> getFreeTerms(@PathVariable String username) {
        return this.reservationService.getFreeTerms(username);
    }

    @GetMapping("/getAllReservations/{username}")
    public List<Reservation> getAllReservationsByUsername(@PathVariable String username) {
        return this.reservationService.getAllReservationsByUsername(username);
    }
}
