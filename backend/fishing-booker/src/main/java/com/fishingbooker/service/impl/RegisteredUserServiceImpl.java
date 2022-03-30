package com.fishingbooker.service.impl;

import java.util.List;

import com.fishingbooker.dto.ApproveUserDTO;
import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.model.ProfileDeletionRequest;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.repository.*;
import org.apache.commons.lang3.ArrayUtils;
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
    private InstructorRepository instructorRepository;
    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;
    @Autowired
    private BoatOwnerRepository boatOwnerRepository;
    @Autowired
    private ProfileDeletionRequestRepository deletionRequestRepository;
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

    public List<ProfileDeletionRequest> findAllDeletionRequests() throws AccessDeniedException {
        return deletionRequestRepository.findAll();
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
        } catch (AuthenticationException e) {
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
        if (newProfile.getPassword() != null && !newProfile.getPassword().isBlank())
            oldProfile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
        this.userRepository.save(oldProfile);
        oldProfile.setPassword("");
        return oldProfile;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    @Override
    public ProfileDeletionRequest saveRequest(ProfileDeletionRequest deletionRequest) {
        return deletionRequestRepository.save(deletionRequest);
    }

    @Override
    public List<ApproveUserDTO> waitingApproval() {
        List<ApproveUserDTO> instructors = instructorRepository.findAllWithoutAdventures();
        List<ApproveUserDTO> cottageOwners = cottageOwnerRepository.findAllWithoutCottages();
        List<ApproveUserDTO> boatOwners = boatOwnerRepository.findAllWithoutBoats();

        List<ApproveUserDTO> unapprovedUsers = List.of(ArrayUtils.addAll(ArrayUtils.addAll(instructors.toArray
                (new ApproveUserDTO[0]), cottageOwners.toArray(new ApproveUserDTO[0])), boatOwners.toArray(new ApproveUserDTO[0])));
        return unapprovedUsers;
    }

    @Override
    public boolean approveUser(String username) {
        RegisteredUser user = userRepository.findByUsername(username);
        if(user == null) return false;
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        RegisteredUser user = userRepository.findByUsername(username);
        ProfileDeletionRequest request = deletionRequestRepository.findByUsername(username);
        if (request != null)
            deletionRequestRepository.delete(request);
        if(user == null) return false;
        userRepository.delete(user);
        return true;
    }

    @Override
    public boolean checkPassword(String username) {
        RegisteredUser user = userRepository.findByUsername(username);
        if(passwordEncoder.matches("admin", user.getPassword()))
            return true;
        return false;
    }

    @Override
    public boolean changePassword(String username, String password) {
        RegisteredUser user = this.userRepository.findByUsername(username);
        if (user == null) return false;
        if (password == null || password.isBlank())
            return false;
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteRequest(String username) {
        ProfileDeletionRequest request = deletionRequestRepository.findByUsername(username);
        if(request == null) return false;
        deletionRequestRepository.delete(request);
        return true;
    }
}
