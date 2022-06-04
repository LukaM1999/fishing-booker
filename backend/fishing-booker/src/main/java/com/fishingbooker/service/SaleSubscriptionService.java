package com.fishingbooker.service;

import com.fishingbooker.model.SaleSubscription;

import java.util.List;

public interface SaleSubscriptionService {

    List<SaleSubscription> getOwnerSubscriptions(String ownerUsername);
    List<SaleSubscription> getCustomerSubscriptions(String customerUsername);
    SaleSubscription createSubscription(SaleSubscription saleSubscription);

    boolean isSubscribed(String entityName, String ownerUsername, String customerUsername);

    void unsubscribe(String entityName, String ownerUsername, String customerUsername);

    List<SaleSubscription> getSubscriptions(String entityName);
}
