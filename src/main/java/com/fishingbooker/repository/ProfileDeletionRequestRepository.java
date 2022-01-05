package com.fishingbooker.repository;

import com.fishingbooker.model.ProfileDeletionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDeletionRequestRepository extends JpaRepository<ProfileDeletionRequest, String> {
}
