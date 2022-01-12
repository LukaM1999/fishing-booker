package com.fishingbooker.repository;

import com.fishingbooker.model.Adventure;
import com.fishingbooker.model.Rentable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentableRepository extends JpaRepository<Rentable, Long> {
    @Query("select cottage from Cottage cottage " +
            "where cottage.ownerUsername = :owner and cottage.name = :name ")
    Rentable getCottageByNameAndOwner(@Param("name") String name, @Param("owner") String owner);

    @Query("select boat from Boat boat " +
            "where boat.boatOwnerUsername = :owner and boat.name = :name ")
    Rentable getBoatByNameAndOwner(@Param("name") String name, @Param("owner") String owner);


    @Query("select adventure from Adventure adventure " +
            "where adventure.instructorUsername = :owner ")
    List<Rentable> getAdventuresByOwner(@Param("owner") String owner);
}
