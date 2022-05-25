package com.fishingbooker.repository;

import com.fishingbooker.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    int countPenaltiesByCustomerUsername(String customerUsername);
    List<Penalty> findPenaltiesByCustomerUsername(String customerUsername);

    @Modifying
    @Transactional
    @Query("delete from Penalty p where p.issuedAt < :firstOfMonth")
    void deletePenaltiesBeforeFirstOfMonth(@Param("firstOfMonth") LocalDateTime firstOfMonth);

}
