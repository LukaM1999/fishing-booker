package com.fishingbooker.service;

import com.fishingbooker.dto.ApproveUserDTO;
import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.dto.RegistrationDTO;
import com.fishingbooker.model.ProfileDeletionRequest;
import com.fishingbooker.model.RegisteredUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RegisteredUserService extends UserDetailsService {
    RegisteredUser findById(Long id);
    @Transactional
    List<RegisteredUser> findAll();
    List<ProfileDeletionRequest> findAllDeletionRequests();
    RegisteredUser register(RegistrationDTO user);

    UserDetails loadUserByUsernameLocked(String username) throws UsernameNotFoundException;

    RegisteredUser findByUsername(String username);
    RegisteredUser save(RegisteredUser userRequest);
    RegisteredUser editProfile(RegisteredUser newProfile);
    ProfileDeletionRequest saveRequest(ProfileDeletionRequest deletionRequest);

    List<ApproveUserDTO> waitingApproval();
    boolean approveUser(String username);

    boolean deleteUser(String username);

    boolean checkPassword(String username);

    boolean changePassword(String username, String password);

    boolean deleteRequest(String username);
}
