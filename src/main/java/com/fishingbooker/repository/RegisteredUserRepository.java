package com.fishingbooker.repository;

import com.fishingbooker.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, String> {
}
