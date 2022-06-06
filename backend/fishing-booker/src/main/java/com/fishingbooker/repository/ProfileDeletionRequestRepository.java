package com.fishingbooker.repository;

import com.fishingbooker.model.ProfileDeletionRequest;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface ProfileDeletionRequestRepository extends JpaRepository<ProfileDeletionRequest, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "0"))
    ProfileDeletionRequest findByUsername(String username);
}
