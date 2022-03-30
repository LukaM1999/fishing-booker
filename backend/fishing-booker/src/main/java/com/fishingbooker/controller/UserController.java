package com.fishingbooker.controller;

import java.util.List;

import com.fishingbooker.dto.ApproveUserDTO;
import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.model.ProfileDeletionRequest;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RegisteredUser findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @Transactional
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RegisteredUser> loadAll() {
        return this.userService.findAll();
    }

    @Transactional
    @GetMapping("/getDeletionRequests")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ProfileDeletionRequest> loadAllDeletionRequests() {
        return this.userService.findAllDeletionRequests();
    }

    @Transactional
    @GetMapping("/waitingApproval")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ApproveUserDTO> waitingApproval() {
        return this.userService.waitingApproval();
    }

    @PutMapping("/editProfile")
    public RegisteredUser editProfile(@RequestBody RegisteredUser user) {
        return this.userService.editProfile(user);
    }

    @PutMapping("/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean approveUser(@RequestBody String username) {
        return this.userService.approveUser(username);
    }

    @PostMapping("/sendDeletionRequest")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'COTTAGE_OWNER', 'BOAT_OWNER', 'INSTRUCTOR')")
    public ProfileDeletionRequest sendDeletionRequest(@RequestBody ProfileDeletionRequest deletionRequest) {
        return this.userService.saveRequest(deletionRequest);
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteUser(@PathVariable String username) {
        return this.userService.deleteUser(username);
    }

    @DeleteMapping("/deleteRequest/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteRequest(@PathVariable String username) {
        return this.userService.deleteRequest(username);
    }


    @PutMapping("/checkPassword")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean checkPassword(@RequestBody String username) {
        return this.userService.checkPassword(username);
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean changePassword(@RequestBody LoginDTO loginDto) {
        return this.userService.changePassword(loginDto.getUsername(), loginDto.getPassword());
    }

}
