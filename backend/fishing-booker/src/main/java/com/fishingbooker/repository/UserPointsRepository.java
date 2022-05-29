package com.fishingbooker.repository;

import com.fishingbooker.model.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointsRepository extends JpaRepository<UserPoints, String> {

    UserPoints findByUsername(String username);
}

