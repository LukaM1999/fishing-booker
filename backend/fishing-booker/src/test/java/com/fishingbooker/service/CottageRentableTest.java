package com.fishingbooker.service;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.repository.CottageRepository;
import com.fishingbooker.repository.ReservationRepository;
import com.fishingbooker.service.impl.CottageServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CottageRentableTest {

    @Autowired
    @InjectMocks
    private CottageServiceImpl cottageService;

    @InjectMocks
    private CottageServiceImpl cottageServiceSpy;

    @Mock
    private CottageRepository cottageRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;

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

    @Test
    public void registerCottageTest() throws IOException {
        Cottage cottage = new Cottage();
        cottage.setName("Water Boofaloo");
        cottage.setPrice(90);
        cottage.setAdditionalServices("voda=5;");
        cottage.setOwnerUsername("Piwneuh");
        cottage.setAddress("Kralja Petra I");
        cottage.setCity("Backa Palanka");
        cottage.setImages("");
        MockMultipartFile[] files = new MockMultipartFile[1];
        for (int i = 0; i < files.length; i++) {
            files[i] = new MockMultipartFile("blob", "blob", "image/jpeg", "Hello World!".getBytes());
        }

        when(cottageRepositoryMock.save(any(Cottage.class))).thenReturn(cottage);

        Cottage created = cottageServiceSpy.registerCottage(cottage, files);
        assertEquals(cottage.getName(), created.getName());

        verify(cottageRepositoryMock, times(2)).save(any(Cottage.class));
        verifyNoMoreInteractions(cottageRepositoryMock);
    }

    @Test
    public void registerCottageNoImagesTest() throws IOException {
        Cottage cottage = new Cottage();
        cottage.setName("Water Boofaloo");
        cottage.setPrice(90);
        cottage.setAdditionalServices("voda=5;");
        cottage.setOwnerUsername("Piwneuh");
        cottage.setAddress("Kralja Petra I");
        cottage.setCity("Backa Palanka");
        cottage.setImages("");
        MockMultipartFile[] files = new MockMultipartFile[0];

        when(cottageRepositoryMock.save(any(Cottage.class))).thenReturn(cottage);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            cottageServiceSpy.registerCottage(cottage, files);
        });

        verify(cottageRepositoryMock, times(1)).save(any(Cottage.class));
        verifyNoMoreInteractions(cottageRepositoryMock);
    }

    @Test
    public void deleteCottageTest() {
        Long cottageId = 3L;

        when(cottageRepositoryMock.findByIdLocked(cottageId)).thenReturn(new Cottage());
        when(cottageRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        assertTrue(cottageServiceSpy.deleteCottage(cottageId));

        verify(cottageRepositoryMock, times(1)).findByIdLocked(any(Long.class));
        verify(reservationRepositoryMock, times(1)).findAll();
        verify(cottageRepositoryMock, times(1)).deleteById(any(Long.class));
        verifyNoMoreInteractions(cottageRepositoryMock);
    }

    @Test
    public void getOwnersCottagesTest() {
        String username = "Piwneuh";
        when(cottageRepositoryMock.findAllByOwnerUsername(username)).thenReturn(new ArrayList<>());
        cottageServiceSpy.findAllByOwnerUsername(username);
        verify(cottageRepositoryMock, times(1)).findAllByOwnerUsername(username);
        verifyNoMoreInteractions(cottageRepositoryMock);
    }

}
