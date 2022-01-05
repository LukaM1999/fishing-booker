package com.fishingbooker.service;

import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.model.RegisteredUser;

import java.util.List;

public interface RegisteredUserService {
    RegisteredUser findById(Long id);
    List<RegisteredUser> findAll ();
    RegisteredUser findByUsername(String username);
    RegisteredUser save(RegisteredUser userRequest);
    boolean isPasswordValid(LoginDTO loginDto);
    RegisteredUser editProfile(RegisteredUser newProfile);
}
