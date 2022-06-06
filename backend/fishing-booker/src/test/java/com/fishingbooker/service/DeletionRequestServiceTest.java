package com.fishingbooker.service;

import com.fishingbooker.dto.RegistrationDTO;
import com.fishingbooker.model.ProfileDeletionRequest;
import com.fishingbooker.model.RegisteredUser;
import com.fishingbooker.repository.ProfileDeletionRequestRepository;
import com.fishingbooker.repository.RegisteredUserRepository;
import com.fishingbooker.service.impl.RegisteredUserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeletionRequestServiceTest {

    @Autowired
    @InjectMocks
    private RegisteredUserServiceImpl deletionRequestService;

    @Mock
    private ProfileDeletionRequestRepository profileDeletionRequestRepository;

    @Mock
    private RegisteredUserRepository registeredUserRepository;

    @Mock
    private ProfileDeletionRequest profileDeletionRequest;

    @Mock
    private RegisteredUser registeredUser;

    @Test
    @Transactional
    @Modifying
    public void pessimisticLockingTest() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                //try { Thread.sleep(14); } catch (InterruptedException e) { e.printStackTrace(); }
                deletionRequestService.deleteUser("imbiamba7");
                 // izvrsavanje transakcione metode traje oko 200 milisekundi
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(40, 0); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
                Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
                    deletionRequestService.deleteUser("imbiamba7");
                });
                assertTrue(exception.getCause() instanceof NullPointerException);
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

}
