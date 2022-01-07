package com.fishingbooker.repository;

import com.fishingbooker.model.FreeTerm;
import com.fishingbooker.model.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreeTermRepository extends JpaRepository<FreeTerm, Long> {

    @Query("select term from FreeTerm term " +
            "where term.type = :type and term.endTime > current_timestamp ")
    List<FreeTerm> getFreeTermsByType(@Param("type") ReservationType type);

}
