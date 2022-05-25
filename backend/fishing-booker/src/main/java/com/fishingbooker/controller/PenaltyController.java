package com.fishingbooker.controller;

import com.fishingbooker.model.Penalty;
import com.fishingbooker.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/penalty", produces = MediaType.APPLICATION_JSON_VALUE)
public class PenaltyController {

    @Autowired
    private PenaltyService penaltyService;

    @GetMapping("/count/{customerUsername}")
    public int countPenaltiesByCustomerUsername(@PathVariable String customerUsername) {
        return penaltyService.countPenaltiesByCustomerUsername(customerUsername);
    }

    @GetMapping("/{customerUsername}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<Penalty> findPenaltiesByCustomerUsername(@PathVariable String customerUsername) {
        return penaltyService.findPenaltiesByCustomerUsername(customerUsername);
    }

    @PostMapping("/add")
    public Penalty createPenalty(@RequestBody Penalty penalty) {
        return penaltyService.createPenalty(penalty);
    }
}
