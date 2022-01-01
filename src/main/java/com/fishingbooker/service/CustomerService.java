package com.fishingbooker.service;

import com.fishingbooker.model.Customer;

public interface CustomerService {
    Customer findByToken(String token);
    void verifyCustomer(String username);
}
