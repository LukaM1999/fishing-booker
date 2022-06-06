package com.fishingbooker.service;

import com.fishingbooker.service.impl.CottageServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OwnerRentableTest {

    @Autowired
    @InjectMocks
    private CottageServiceImpl cottageService;

    @Test
    @Transactional
    @Modifying
    public void deleteCottagePessimisticLockingTest() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                //try { Thread.sleep(14); } catch (InterruptedException e) { e.printStackTrace(); }
                cottageService.deleteCottage(3L);
                // izvrsavanje transakcione metode traje oko 200 milisekundi
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(40, 0); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
                Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
                        cottageService.deleteCottage(3L);
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
