package com.fishingbooker.repository;

import com.fishingbooker.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsRepository extends JpaRepository<Points, Long> {

}
