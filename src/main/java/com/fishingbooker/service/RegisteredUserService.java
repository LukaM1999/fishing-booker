package com.fishingbooker.service;

import com.fishingbooker.dto.ApproveUserDTO;
import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.model.ProfileDeletionRequest;
import com.fishingbooker.model.RegisteredUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RegisteredUserService {
    RegisteredUser findById(Long id);
    @Transactional
    List<RegisteredUser> findAll();
    RegisteredUser findByUsername(String username);
    RegisteredUser save(RegisteredUser userRequest);
    boolean isPasswordValid(LoginDTO loginDto);
    RegisteredUser editProfile(RegisteredUser newProfile);
    ProfileDeletionRequest saveRequest(ProfileDeletionRequest deletionRequest);

    List<ApproveUserDTO> waitingApproval();
    boolean approveUser(String username);

    boolean deleteUser(String username);
}
