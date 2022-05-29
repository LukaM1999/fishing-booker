package com.fishingbooker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import com.fishingbooker.config.TestConfig;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.model.Role;
import com.fishingbooker.repository.RegisteredUserRepository;
import com.fishingbooker.service.RegisteredUserService;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisteredUserServiceTest {

    @Mock
    private RegisteredUserRepository registeredUserRepositoryMock;

    @Mock
    private RegisteredUser registeredUserMock;

    @InjectMocks
    private RegisteredUserServiceImpl registeredUserService;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Test
    public void findByIdTest() {

        when(registeredUserRepositoryMock.findById(1L)).thenReturn(Optional.of(registeredUserMock));
        RegisteredUser registeredUser = registeredUserService.findById(1L);
        assertEquals(registeredUserMock, registeredUser);

        verify(registeredUserRepositoryMock, times(1)).findById(1L);
        verifyNoMoreInteractions(registeredUserRepositoryMock);
    }

    @Test
    public void findByUsernameTest() {

        when(registeredUserRepositoryMock.findByUsername("imbiamba")).thenReturn(registeredUserMock);
        RegisteredUser registeredUser = registeredUserService.findByUsername("imbiamba");
        assertEquals(registeredUserMock, registeredUser);

        verify(registeredUserRepositoryMock, times(1)).findByUsername("imbiamba");
        verifyNoMoreInteractions(registeredUserRepositoryMock);
    }

    @Test
    public void saveTest() {
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername("username");
        registeredUser.setPassword("password123!");
        registeredUser.setEmail("email.test.fishing@gmail.com");
        registeredUser.setName("First");
        registeredUser.setSurname("Last");
        registeredUser.setPhone("065123123");
        registeredUser.setRole(new Role("CUSTOMER"));


        when(registeredUserRepositoryMock.save(registeredUser)).thenReturn(registeredUser);
        RegisteredUser saved = registeredUserService.save(registeredUser);
        assertEquals(saved.getUsername(), registeredUser.getUsername());

        verify(registeredUserRepositoryMock, times(1)).save(registeredUser);
        verifyNoMoreInteractions(registeredUserRepositoryMock);
    }

    @Test
    @Transactional
    public void editProfileTest() {
        when(registeredUserRepositoryMock.findByUsername("imbiamba")).thenReturn(registeredUserMock);

        RegisteredUser registeredUser = registeredUserService.findByUsername("imbiamba");
        registeredUser.setName("NewName");
        registeredUser.setSurname("NewSurname");
        registeredUser.setPhone("123456789");

        when(registeredUserRepositoryMock.save(registeredUser)).thenReturn(registeredUser);

        RegisteredUser saved = registeredUserService.save(registeredUser);

        assertEquals(saved.getName(), registeredUser.getName());
        assertEquals(saved.getSurname(), registeredUser.getSurname());
        assertEquals(saved.getPhone(), registeredUser.getPhone());

        verify(registeredUserRepositoryMock, times(1)).findByUsername("imbiamba");
        verify(registeredUserRepositoryMock, times(1)).save(registeredUser);
        verifyNoMoreInteractions(registeredUserRepositoryMock);
    }
}
