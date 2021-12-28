package com.fishingbooker.controller;

import javax.servlet.http.HttpServletResponse;

import com.fishingbooker.dto.JwtDTO;
import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import com.fishingbooker.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegisteredUserServiceImpl userService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> createAuthenticationToken(
            @RequestBody LoginDTO loginDto, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        RegisteredUser user = (RegisteredUser) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new JwtDTO(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<RegisteredUser> addUser(@RequestBody RegisteredUser registeredUser, UriComponentsBuilder ucBuilder) {

        UserDetails existUser = this.userService.loadUserByUsername(registeredUser.getUsername());

        if (existUser != null) {
            throw new NullPointerException("Username already exists: " + registeredUser.getUsername());
        }

        RegisteredUser user = this.userService.save(registeredUser);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
