package com.fishingbooker.service.impl;

import java.util.List;
import java.util.UUID;

import com.fishingbooker.dto.ApproveUserDTO;
import com.fishingbooker.dto.LoginDTO;
import com.fishingbooker.dto.RegistrationDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.*;
import com.fishingbooker.service.PointsService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {


    private final RegisteredUserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final CottageOwnerRepository cottageOwnerRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final ProfileDeletionRequestRepository deletionRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final PointsService pointsService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RegisteredUserServiceImpl(RegisteredUserRepository userRepository, InstructorRepository instructorRepository,
                                     CottageOwnerRepository cottageOwnerRepository, BoatOwnerRepository boatOwnerRepository,
                                     ProfileDeletionRequestRepository deletionRequestRepository, PasswordEncoder passwordEncoder,
                                     PointsService pointsService) {
        this.userRepository = userRepository;
        this.instructorRepository = instructorRepository;
        this.cottageOwnerRepository = cottageOwnerRepository;
        this.boatOwnerRepository = boatOwnerRepository;
        this.deletionRequestRepository = deletionRequestRepository;
        this.passwordEncoder = passwordEncoder;
        this.pointsService = pointsService;
    }

    public RegisteredUser findById(Long id) throws AccessDeniedException {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<RegisteredUser> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    public List<ProfileDeletionRequest> findAllDeletionRequests() throws AccessDeniedException {
        return deletionRequestRepository.findAll();
    }

    private static void setTimeoutSync(Runnable runnable, int delay) {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    @Transactional
    @Override
    public RegisteredUser register(RegistrationDTO registrationDTO) {
        try {
            UserDetails existUser = loadUserByUsernameLocked(registrationDTO.getUsername());

            if (existUser != null) {
                return null;
            }

            registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

            RegisteredUser user;
            switch (registrationDTO.getRole()) {
                case "CUSTOMER": {
                    registrationDTO.setVerificationToken(UUID.randomUUID().toString());
                    user = save(new Customer(registrationDTO));
                    break;
                }
                case "COTTAGE_OWNER": {
                    user = save(new CottageOwner(registrationDTO));
                    break;
                }
                case "INSTRUCTOR": {
                    user = save(new Instructor(registrationDTO));
                    break;
                }
                case "BOAT_OWNER": {
                    user = save(new BoatOwner(registrationDTO));
                    break;
                }
                case "ADMIN": {
                    user = save(new Admin(registrationDTO));
                    approveUser(registrationDTO.getUsername());
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unrecognized user role!");

            }
            pointsService.createUserPoints(new UserPoints(registrationDTO.getUsername(), 0));
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Cacheable(value = "user")
    public RegisteredUser findByUsername(String username) {
        logger.info("findByUsername: " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public RegisteredUser save(RegisteredUser user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Pessimistic lock exception: " + e.getMessage());
            return null;
        }
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

    @Transactional
    @Override
    public UserDetails loadUserByUsernameLocked(String s) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsernameLocked(s);
        } catch (Exception e) {
            System.out.println("Pessimistic lock exception: " + e.getMessage());
            return null;
        }
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

    @Transactional
    @Override
    public boolean deleteUser(String username) {
        try {
            logger.info("Deleting user: " + username);
            RegisteredUser user = userRepository.findByUsernameLocked(username);
            logger.info("User found: " + user.getUsername());
            ProfileDeletionRequest request = deletionRequestRepository.findByUsername(username);
            if (request != null)
                deletionRequestRepository.delete(request);
            if(user == null) return false;
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    @Transactional
    @Override
    public boolean deleteRequest(String username) {
        try {
            ProfileDeletionRequest request = deletionRequestRepository.findByUsername(username);
            if(request == null) return false;
            deletionRequestRepository.delete(request);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}
