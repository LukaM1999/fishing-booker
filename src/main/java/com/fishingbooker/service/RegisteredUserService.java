package com.fishingbooker.service;

import com.fishingbooker.model.RegisteredUser;

import java.util.List;

public interface RegisteredUserService {
    RegisteredUser findById(Long id);
    List<RegisteredUser> findAll ();
    RegisteredUser findByUsername(String username);
    RegisteredUser save(RegisteredUser userRequest);
}
