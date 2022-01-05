package com.fishingbooker.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fishingbooker.model.Customer;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class UserController {

    @Autowired
    private RegisteredUserServiceImpl userService;

    // Za pristup ovoj metodi neophodno je da ulogovani korisnik ima ADMIN ulogu
    // Ukoliko nema, server ce vratiti gresku 403 Forbidden
    // Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RegisteredUser loadById(@PathVariable Long userId) {
        return this.userService.findById(userId);
    }

    @GetMapping("/all")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public List<RegisteredUser> loadAll() {
        return this.userService.findAll();
    }

    @PutMapping("/editProfile")
    public RegisteredUser editProfile(@RequestBody RegisteredUser user) {
        return this.userService.editProfile(user);
    }
}
