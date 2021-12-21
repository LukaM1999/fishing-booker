package com.fishingbooker.services;

import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @GetMapping(path = "/getUsers")
    public ResponseEntity<List<RegisteredUser>> getUsers() {
        return new ResponseEntity<>(registeredUserRepository.findAll(), HttpStatus.OK);
    }
}
