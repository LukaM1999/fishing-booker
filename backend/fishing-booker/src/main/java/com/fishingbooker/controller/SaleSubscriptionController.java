package com.fishingbooker.controller;

import com.fishingbooker.model.SaleSubscription;
import com.fishingbooker.service.SaleSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saleSubscription")
public class SaleSubscriptionController {

    @Autowired
    private SaleSubscriptionService saleSubscriptionService;

    @GetMapping("/customer/{customerUsername}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public List<SaleSubscription> getCustomerSubscriptions(@PathVariable String customerUsername) {
        return saleSubscriptionService.getCustomerSubscriptions(customerUsername);
    }

    @GetMapping("/owner/{ownerUsername}")
    @PreAuthorize("!hasAnyAuthority('ADMIN','CUSTOMER')")
    public List<SaleSubscription> getOwnerSubscriptions(@PathVariable String ownerUsername) {
        return saleSubscriptionService.getOwnerSubscriptions(ownerUsername);
    }

    @GetMapping("/isSubscribed")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public boolean isSubscribed(@RequestParam String entityName, @RequestParam String ownerUsername, @RequestParam String customerUsername) {
        return saleSubscriptionService.isSubscribed(entityName, ownerUsername, customerUsername);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public SaleSubscription createSubscription(@RequestBody SaleSubscription saleSubscription) {
        return saleSubscriptionService.createSubscription(saleSubscription);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void unsubscribe(@RequestParam String entityName, @RequestParam String ownerUsername, @RequestParam String customerUsername) {
        saleSubscriptionService.unsubscribe(entityName, ownerUsername, customerUsername);
    }
}
