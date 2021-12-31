package com.fishingbooker.service;

import com.fishingbooker.model.Customer;

public interface CustomerService {
    public Customer findByToken(String token);
    public void verifyCustomer(String username);
}
