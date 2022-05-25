package com.fishingbooker.service;

import com.fishingbooker.model.Complaint;

import java.util.List;
import java.util.Optional;

public interface ComplaintService {

    Complaint createComplaint(Complaint complaint);

    List<Complaint> findAllByIssuerUsername(String username);

    List<Complaint> findAll();

    Optional<Complaint> findById(Long id);

    Complaint updateComplaint(Complaint complaint);
}
