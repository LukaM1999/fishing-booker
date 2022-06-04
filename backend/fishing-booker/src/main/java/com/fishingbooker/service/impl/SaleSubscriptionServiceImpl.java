package com.fishingbooker.service.impl;

import com.fishingbooker.model.SaleSubscription;
import com.fishingbooker.repository.SaleSubscriptionRepository;
import com.fishingbooker.service.SaleSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleSubscriptionServiceImpl implements SaleSubscriptionService {

    @Autowired
    private SaleSubscriptionRepository saleSubscriptionRepository;

    @Override
    public List<SaleSubscription> getOwnerSubscriptions(String ownerUsername) {
        return saleSubscriptionRepository.findByOwnerUsername(ownerUsername);
    }

    @Override
    public List<SaleSubscription> getCustomerSubscriptions(String customerUsername) {
        return saleSubscriptionRepository.findByCustomerUsername(customerUsername);
    }

    @Override
    public SaleSubscription createSubscription(SaleSubscription saleSubscription) {
        if(saleSubscriptionRepository.findByEntityNameAndOwnerUsernameAndCustomerUsername(
                saleSubscription.getEntityName(), saleSubscription.getOwnerUsername(),
                saleSubscription.getCustomerUsername()) != null) {
            return null;
        }
        return saleSubscriptionRepository.save(saleSubscription);
    }

    @Override
    public boolean isSubscribed(String entityName, String ownerUsername, String customerUsername) {
        return saleSubscriptionRepository.findByEntityNameAndOwnerUsernameAndCustomerUsername(
                entityName, ownerUsername, customerUsername) != null;
    }

    @Override
    public void unsubscribe(String entityName, String ownerUsername, String customerUsername) {
        saleSubscriptionRepository.deleteSaleSubscriptionByEntityNameAndOwnerUsernameAndCustomerUsername(
                entityName, ownerUsername, customerUsername);
    }

    @Override
    public List<SaleSubscription> getSubscriptions(String entityName) {
       return saleSubscriptionRepository.findByEntityName(entityName);
    }
}
