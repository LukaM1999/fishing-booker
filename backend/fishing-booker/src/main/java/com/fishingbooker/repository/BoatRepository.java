package com.fishingbooker.repository;

import com.fishingbooker.model.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatRepository extends JpaRepository<Boat, Long> {

    Boat getBoatByNameAndOwnerUsername(String name, String owner);
}
