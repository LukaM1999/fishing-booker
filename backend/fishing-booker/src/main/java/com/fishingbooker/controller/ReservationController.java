package com.fishingbooker.controller;

import com.fishingbooker.dto.ActionDTO;
import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.EventDTO;
import com.fishingbooker.dto.FreeTermDTO;
import com.fishingbooker.dto.ReservationHistoryDTO;
import com.fishingbooker.dto.ReserveActionDTO;
import com.fishingbooker.dto.statistics.FinanceDTO;
import com.fishingbooker.dto.statistics.ReservationNumDTO;
import com.fishingbooker.dto.statistics.TimeDTO;
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

    @PostMapping("/createAction/{rentableId}")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public Reservation createAction(@PathVariable Long rentableId, @RequestBody Reservation reservation){
        FreeTerm freeTerm = new FreeTerm();
        freeTerm.setStartTime(reservation.getStartTime());
        freeTerm.setEndTime(reservation.getEndTime());
        freeTerm.setOwnerUsername(reservation.getOwnerUsername());
        freeTerm.setType(reservation.getType());
        freeTerm.setEntityName(reservation.getName());

        reservationService.createFreeTerm(freeTerm);
        return reservationService.reserveRentable(rentableId, reservation);
    }

    @PostMapping("/getActions")
    public List<Reservation> getActions(@RequestBody ActionDTO actionDTO){
        return reservationService.getActions(actionDTO.getName(), actionDTO.getOwnerUsername());
    }

    @PatchMapping("/reserveAction")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void reserveAction(@RequestBody ReserveActionDTO reserveActionDTO){
        this.reservationService.reserveAction(reserveActionDTO.getId(), reserveActionDTO.getCustomerUsername());
    }

    @PostMapping("/getFinishedReservations")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public List<Reservation> getFinishedReservations(@RequestBody ReservationHistoryDTO historyDto){
        return reservationService.getFinishedReservations(historyDto.getType(), historyDto.getUsername(), historyDto.getIsCustomer());
    }

    @PostMapping("/getReservations")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public List<Reservation> getReservations(@RequestBody ReservationHistoryDTO historyDto){
        return reservationService.getReservations(historyDto.getType(), historyDto.getUsername(), historyDto.getIsCustomer());
    }

    @GetMapping("/getNumberOfReservations/{username}")
    public List<ReservationNumDTO> getNumberOfReservations(@PathVariable String username){
        return reservationService.getNumberOfReservations(username);
    }

    @PostMapping("/finances")
    public List<FinanceDTO> getFinances(@RequestBody TimeDTO timeDTO){
        return reservationService.getFinances(timeDTO.getUsername(), timeDTO.getStartTime(), timeDTO.getEndTime());
    }

    @PostMapping("/getFreeTerms")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public List<FreeTerm> getFreeTerms(@RequestBody EventDTO event) {
        return this.reservationService.getFreeTerms(event);
    }

    @PostMapping("/getAllReservations")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public List<Reservation> getAllReservations(@RequestBody EventDTO event) {
        return this.reservationService.getAllReservations(event);
    }

    @PostMapping("/review")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void addReview(@RequestBody Review review) {
        this.reservationService.addReview(review);
    }

    @GetMapping("/getReviews")
    public List<Review> getReviews() {
        return this.reservationService.getReviews();
    }

    @PutMapping("/updateReview")
    public void updateReview(@RequestBody Review review) {
        this.reservationService.updateReview(review);
    }

    @PostMapping("/createDayOff")
    public List<FreeTerm> createDayOff(@RequestBody FreeTermDTO dto){
        return this.reservationService.createDayOff(dto);
    }

    @PatchMapping("/cancelReservation/{reservationId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void cancelReservation(@PathVariable Long reservationId) {
        this.reservationService.cancelReservation(reservationId);
    }

    @GetMapping("/{cottageId}")
    public Cottage getCottage(@PathVariable Long cottageId) {
        return this.reservationService.getCottageById(cottageId);
    }

}
