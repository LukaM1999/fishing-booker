package com.fishingbooker.repository;

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
            "r.type = 'ADVENTURE' and r.ownerUsername = :owner " +
            "and (:startTime between r.startTime and r.endTime or " +
            ":endTime between r.startTime and r.endTime)")
    Reservation getOccupiedAdventure(@Param("owner") String owner, @Param("startTime")LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);

    @Query("select r from Reservation r where " +
            "r.type = :type and r.customerUsername = :username " +
            "and r.endTime <= current_timestamp ")
    List<Reservation> getFinishedCustomerReservations(@Param("type") ReservationType type, @Param("username") String username);

    @Query("select r from Reservation r where " +
            "r.type = :type and r.customerUsername = :username")
    List<Reservation> getCustomerReservations(@Param("type") ReservationType type, @Param("username") String username);

    @Query("select r from Reservation r where " +
            "r.ownerUsername = :username " +
            "and r.endTime <= current_timestamp ")
    List<Reservation> getFinishedOwnerReservations(@Param("username") String username);

    @Query("select r from Reservation r where " +
            "r.ownerUsername = :username")
    List<Reservation> getOwnerReservations(@Param("username") String username);

    List<Reservation> getAllByOwnerUsername(String username);

    Reservation getById(Long id);

    @Query("select r from Reservation r where " +
            "r.ownerUsername = :username " +
            "and r.name = :rentableName")
    List<Reservation> getAllByNameAndUsername(String rentableName, String username);
    
    @Query( "select r from Reservation r where " +
            "r.ownerUsername = :ownerUsername " +
            "and r.name = :name and r.isDeal = true " +
            "and r.startTime > current_timestamp " +
            "and r.customerUsername is null")
    List<Reservation> getActions(@Param("name") String name, @Param("ownerUsername") String ownerUsername);

    Reservation getReservationById(Long id);
}
