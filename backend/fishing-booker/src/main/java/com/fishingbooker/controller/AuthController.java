package com.fishingbooker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fishingbooker.dto.JwtDTO;
import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.dto.RegistrationDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.service.PointsService;
import com.fishingbooker.service.impl.CustomerServiceImpl;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import com.fishingbooker.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;


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

    @Autowired
    private CustomerServiceImpl customerService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> createAuthenticationToken(
            @RequestBody LoginDTO loginDto) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        RegisteredUser user = (RegisteredUser) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        user.setPassword(null);
        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new JwtDTO(user, jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<Object> addUser(@RequestBody RegistrationDTO registrationDTO) {
        RegisteredUser user = userService.register(registrationDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<Object> addUser(@RequestParam String token, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, URISyntaxException {
        Customer customer = customerService.findByToken(token);
        if (customer == null) throw new NullPointerException("Username with this token doesn't exist!");
        if (customer.isEnabled()) throw new IllegalAccessException("Account is already verified!");
        customerService.verifyCustomer(customer.getUsername());
        HttpHeaders httpHeaders = new HttpHeaders();
        if(Objects.requireNonNull(request.getRequestURL()).toString().contains("localhost")) {
            httpHeaders.setLocation(new URI("http://localhost:7000/login"));
        } else {
            httpHeaders.setLocation(new URI("https://fishing-booker.herokuapp.com/login"));
        }
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @PostMapping("/confirmPassword")
    public boolean confirmPassword(@RequestBody LoginDTO loginDto) {
        return userService.isPasswordValid(loginDto);
    }
}
