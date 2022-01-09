package com.fishingbooker.repository;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.dto.FreeTermDTO;
import com.fishingbooker.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where " +
            "r.name = :name and r.ownerUsername = :owner " +
            "and (:startTime between r.startTime and r.endTime or " +
            ":endTime between r.startTime and r.endTime)")
    Reservation getOccupied(@Param("name") String name, @Param("owner") String owner,
                            @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select r from Reservation r where " +
            "r.type = :type and r.ownerUsername = :owner " +
            "and (:startTime between r.startTime and r.endTime or " +
            ":endTime between r.startTime and r.endTime)")
    Reservation getOccupiedAdventure(@Param("type") ReservationType type, @Param("owner") String owner,
                                     @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}