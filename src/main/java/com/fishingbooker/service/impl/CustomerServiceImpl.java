package com.fishingbooker.service.impl;

import com.fishingbooker.model.Customer;
import com.fishingbooker.repository.CustomerRepository;
import com.fishingbooker.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findByToken(String token) {
        return customerRepository.findByVerificationToken(token);
    }

    @Override
    public void verifyCustomer(String username) {
        customerRepository.verifyCustomer(username);
    }
}
