package com.fishingbooker.repository;

import com.fishingbooker.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    RegisteredUser findByUsername(String username);

    @Override
    Optional<RegisteredUser> findById(Long aLong);
}
