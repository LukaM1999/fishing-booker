package com.fishingbooker.repository;

import com.fishingbooker.model.RegisteredUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

    @Cacheable("user")
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    RegisteredUser findByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from RegisteredUser u where u.username = :username")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    RegisteredUser findByUsernameLocked(String username);

    RegisteredUser getUserByUsername(String username);
}
