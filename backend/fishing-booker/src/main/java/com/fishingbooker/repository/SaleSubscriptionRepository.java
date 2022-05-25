package com.fishingbooker.repository;

import com.fishingbooker.model.SaleSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface SaleSubscriptionRepository extends JpaRepository<SaleSubscription, Long> {

    List<SaleSubscription> findByOwnerUsername(String ownerUsername);
    List<SaleSubscription> findByCustomerUsername(String customerUsername);

    SaleSubscription findByEntityNameAndOwnerUsernameAndCustomerUsername(String entityName, String ownerUsername, String customerUsername);

    @Transactional
    @Modifying
    void deleteSaleSubscriptionByEntityNameAndOwnerUsernameAndCustomerUsername(String entityName, String ownerUsername, String customerUsername);
}
