package com.fishingbooker.controller;

import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @CrossOrigin
    @PostMapping(path = "/registerUser", consumes="application/json")
    public void registerUser(@RequestBody RegisteredUser registeredUser){
        registeredUserRepository.save(registeredUser);
    }
}
