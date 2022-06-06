package com.fishingbooker.service;


import com.fishingbooker.model.Reservation;
import com.fishingbooker.model.ReservationType;
import com.fishingbooker.service.impl.ReservationServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OwnerReservationTest {

    @Autowired
    @InjectMocks
    private ReservationServiceImpl reservationServiceSpy;

    @Test
    public void reserveRentablePessimisticLockTest() throws Throwable {

        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        reservation.setOwnerUsername("Piwneuh");
        reservation.setName("Gold Mirror");
        reservation.setType(ReservationType.COTTAGE);
        reservation.setStartTime(LocalDateTime.parse("2022-08-27T00:00:00"));
        reservation.setEndTime(LocalDateTime.parse("2022-08-29T00:00:00"));
        reservation.setPrice(90);
        reservation.setGuests(1);
        reservation.setAdditionalServices("");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Reservation reserved = reservationServiceSpy.reserveRentable(3L, reservation);
                assertEquals(reservation.getName(), reserved.getName());
                assertEquals(reservation.getType(), reserved.getType());
                assertEquals(reservation.getStartTime(), reserved.getStartTime());
                assertEquals(reservation.getEndTime(), reserved.getEndTime());

            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(60, 500000); } catch (InterruptedException e) {}
                Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
                    reservationServiceSpy.reserveRentable(3L, reservation);
                });
                assertTrue(exception.getCause() instanceof PessimisticLockingFailureException);
                //assertNull(notReserved);
            }
        });
        try {
            future1.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    @Test
    public void createActionPessimisticLockTest() throws Throwable {

        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        reservation.setOwnerUsername("Piwneuh");
        reservation.setName("Gold Mirror");
        reservation.setType(ReservationType.COTTAGE);
        reservation.setStartTime(LocalDateTime.parse("2022-08-23T00:00:00"));
        reservation.setEndTime(LocalDateTime.parse("2022-08-25T00:00:00"));
        reservation.setPrice(90);
        reservation.setGuests(1);
        reservation.setAdditionalServices("");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Reservation reserved = reservationServiceSpy.reserveRentable(3L, reservation);
                assertEquals(reservation.getName(), reserved.getName());
                assertEquals(reservation.getType(), reserved.getType());
                assertEquals(reservation.getStartTime(), reserved.getStartTime());
                assertEquals(reservation.getEndTime(), reserved.getEndTime());

            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                reservation.setDeal(true); //make reservation into action/sale
                try { Thread.sleep(60, 500000); } catch (InterruptedException e) {}
                Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
                    reservationServiceSpy.reserveRentable(3L, reservation);
                });
                assertTrue(exception.getCause() instanceof PessimisticLockingFailureException);
                //assertNull(notReserved);
            }
        });
        try {
            future1.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
