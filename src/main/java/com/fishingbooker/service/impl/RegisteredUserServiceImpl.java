package com.fishingbooker.service.impl;

import java.util.List;

import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.model.Role;
import com.fishingbooker.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.fishingbooker.service.RegisteredUserService;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService, UserDetailsService {

    @Autowired
    private RegisteredUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisteredUser findById(Long id) throws AccessDeniedException {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<RegisteredUser> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    @Override
    public RegisteredUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public RegisteredUser save(RegisteredUser user) {
        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public boolean isPasswordValid(LoginDTO loginDto) {
//        RegisteredUser user = userRepository.findByUsername(loginDto.getUsername());
//        if (user == null) return false;
//        String confirmedPassword = passwordEncoder.encode(loginDto.getPassword());
//        return user.getPassword().equals(passwordEncoder.encode(loginDto.getPassword()));
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()));
        }catch (AuthenticationException e) {
            return false;
        }
        return true;
    }

    @Override
    public RegisteredUser editProfile(RegisteredUser newProfile) {
        RegisteredUser oldProfile = this.userRepository.findByUsername(newProfile.getUsername());
        if (oldProfile == null) return null;
        oldProfile.setName(newProfile.getName());
        oldProfile.setSurname(newProfile.getSurname());
        oldProfile.setCountry(newProfile.getCountry());
        oldProfile.setCity(newProfile.getCity());
        oldProfile.setAddress(newProfile.getAddress());
        oldProfile.setPhone(newProfile.getPhone());
        oldProfile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
        this.userRepository.save(oldProfile);
        oldProfile.setPassword("");
        return oldProfile;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}
