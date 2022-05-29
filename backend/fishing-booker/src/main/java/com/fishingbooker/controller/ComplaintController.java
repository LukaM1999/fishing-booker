package com.fishingbooker.controller;

import com.fishingbooker.model.Complaint;
import com.fishingbooker.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Complaint> getAll() {
        return this.complaintService.findAll();
    }

    @GetMapping("/{reservationId}")
    public List<Complaint> getById(@PathVariable Long reservationId) {
        return this.complaintService.getById(reservationId);
    }

    @PostMapping(value = "/add")
    @PreAuthorize("!hasAuthority('ADMIN')")
    public Complaint create(@RequestBody Complaint complaint) {
        return this.complaintService.createComplaint(complaint);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Complaint update(@RequestBody Complaint complaint) {
        return this.complaintService.updateComplaint(complaint);
    }

    @GetMapping(value = "/customer/{reservationId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public Complaint getCustomerComplaint(@PathVariable Long reservationId) {
        return this.complaintService.getCustomerComplaint(reservationId);
    }
}
