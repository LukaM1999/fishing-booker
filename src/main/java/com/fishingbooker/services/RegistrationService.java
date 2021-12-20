package com.fishingbooker.services;

import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @CrossOrigin
    @PostMapping(path = "/registerUser", consumes="application/json")
    public void registerUser(@RequestBody RegisteredUser registeredUser){
        registeredUserRepository.save(registeredUser);
    }
}
