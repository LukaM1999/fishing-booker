package com.fishingbooker.repository;

import com.fishingbooker.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findAllByIssuerUsername(String username);

}
