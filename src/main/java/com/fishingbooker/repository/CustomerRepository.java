package com.fishingbooker.repository;

import com.fishingbooker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByVerificationToken(String token);

    @Transactional
    @Modifying
    @Query("update Customer c set c.enabled = true, c.verificationToken = null WHERE c.username = :username")
    void verifyCustomer(@Param("username") String username);
}
