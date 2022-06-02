package com.fishingbooker.repository;

import com.fishingbooker.model.Boat;
import com.fishingbooker.model.Rentable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Long> {

    Boat getBoatByNameAndOwnerUsername(String name, String owner);

    List<Rentable> getRentablesByOwnerUsername(String username);
}
