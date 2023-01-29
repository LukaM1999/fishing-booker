package com.fishingbooker.controller;

import com.fishingbooker.model.OrderStatus;
import com.fishingbooker.model.Reservation;
import com.fishingbooker.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final ReservationService reservationService;

    @Autowired
    public OrderController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PutMapping("/{orderId}")
    public Reservation updateOrderStatus(@PathVariable String orderId, @PathParam("status") OrderStatus status) {
        return reservationService.updateOrderStatus(orderId, status);
    }
}
