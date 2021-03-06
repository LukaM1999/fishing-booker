package com.fishingbooker.repository;

import com.fishingbooker.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findAllByIssuerUsername(String username);

    List<Complaint> findAllByReservationId( Long id);

    @Query("SELECT c FROM Complaint c WHERE c.reservationId = :reservationId AND c.isFromCustomer = true")
    Complaint getCustomerComplaint(@Param("reservationId") Long reservationId);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT c FROM Complaint c WHERE c.id = :id")
    Complaint findByIdLocked(Long id);

}
