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
    public Complaint getById(@PathVariable Long reservationId) {
        return this.complaintService.findById(reservationId).orElseThrow();
    }

    @PostMapping(value = "/add")
    public Complaint create(@RequestBody Complaint complaint) {
        return this.complaintService.createComplaint(complaint);
    }

    @PutMapping(value = "/update")
    public Complaint update(@RequestBody Complaint complaint) {
        return this.complaintService.updateComplaint(complaint);
    }

}
