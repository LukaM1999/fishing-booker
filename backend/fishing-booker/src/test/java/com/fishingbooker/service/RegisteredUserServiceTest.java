package com.fishingbooker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.fishingbooker.config.TestConfig;
import com.fishingbooker.dto.RegistrationDTO;
import com.fishingbooker.model.Customer;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.model.Role;
import com.fishingbooker.model.UserPoints;
import com.fishingbooker.repository.PointsRepository;
import com.fishingbooker.repository.RegisteredUserRepository;
import com.fishingbooker.repository.UserPointsRepository;
import com.fishingbooker.service.RegisteredUserService;
import com.fishingbooker.service.impl.PointsServiceImpl;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisteredUserServiceTest {

    @Mock
    private RegisteredUserRepository registeredUserRepositoryMock;

    @Mock
    private UserPointsRepository pointsRepositoryMock;

    @Mock
    private RegisteredUser registeredUserMock;

    @Mock
    private UserPoints userPointsMock;

    @Mock
    private Customer customerMock;

    @InjectMocks
    private RegisteredUserServiceImpl registeredUserService;

    @Autowired
    @InjectMocks
    private RegisteredUserServiceImpl registeredUserServiceSpy;

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

    //@Test
    public void pessimisticLockingTest() throws Throwable {
        RegistrationDTO registeredUser = new RegistrationDTO();
        registeredUser.setUsername("imbiamba");
        registeredUser.setPassword("password123!");
        registeredUser.setEmail("online.fishing.booker@gmail.com");
        registeredUser.setName("First");
        registeredUser.setSurname("Last");
        registeredUser.setPhone("065123123");
        registeredUser.setRole("CUSTOMER");
        registeredUserMock.setUsername("username");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                //try { Thread.sleep(14); } catch (InterruptedException e) { e.printStackTrace(); }
                RegisteredUser saved = registeredUserServiceSpy.register(registeredUser);
                assertNull(saved); // izvrsavanje transakcione metode traje oko 200 milisekundi
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(10, 0); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi

                RegisteredUser saved = registeredUserServiceSpy.register(registeredUser);
                assertNull(saved);
            }
        });
        try {
            future2.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
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
