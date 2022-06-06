package com.fishingbooker.service;

import com.fishingbooker.model.*;
import com.fishingbooker.repository.FreeTermRepository;
import com.fishingbooker.repository.PenaltyRepository;
import com.fishingbooker.repository.RentableRepository;
import com.fishingbooker.repository.ReservationRepository;
import com.fishingbooker.service.impl.ReservationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Mock
    private PenaltyRepository penaltyRepositoryMock;

    @Mock
    private RentableRepository rentableRepositoryMock;

    @Mock
    private FreeTermRepository freeTermRepositoryMock;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Autowired
    @InjectMocks
    private ReservationServiceImpl reservationServiceSpy;

    @Mock
    private PenaltyService penaltyServiceMock;

    @Mock
    private Reservation reservationMock;

    @Mock
    private Rentable rentableMock;

    @Mock
    private FreeTerm freeTermMock;

    @Mock
    private PointsService pointsServiceMock;

    @Mock
    private UserPoints userPointsMock;

    @Test
    public void getCustomerReservationsTest() {
        when(reservationRepositoryMock.getFinishedCustomerReservations(ReservationType.COTTAGE, "imbiamba")).thenReturn(List.of(reservationMock));
        List<Reservation> reservations = reservationService.getFinishedReservations(ReservationType.COTTAGE,"imbiamba", true);

        assertEquals(1, reservations.size());
        assertEquals(Optional.of(reservationMock), Optional.of(reservations.get(0)));

        verify(reservationRepositoryMock, times(1)).getFinishedCustomerReservations(ReservationType.COTTAGE, "imbiamba");
        verifyNoMoreInteractions(reservationRepositoryMock);
    }

    @Test
    public void reserveCottageTest() {
        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        reservation.setOwnerUsername("Piwneuh");
        reservation.setName("Prvenstvo Srbije");
        reservation.setType(ReservationType.COTTAGE);
        reservation.setStartTime(LocalDateTime.parse("2022-06-23T00:00:00"));
        reservation.setEndTime(LocalDateTime.parse("2022-06-26T00:00:00"));
        reservation.setPrice(90);
        reservation.setGuests(1);
        reservation.setAdditionalServices("");

        Cottage cottage = new Cottage();
        cottage.setName("Prvenstvo Srbije");
        cottage.setOwnerUsername("Piwneuh");
        cottage.setPrice(30);


        when(rentableRepositoryMock.getCottageLock(1L)).thenReturn(cottage);
        when(penaltyServiceMock.countPenaltiesByCustomerUsername("imbiamba")).thenReturn(1);
        when(penaltyRepositoryMock.countPenaltiesByCustomerUsername("imbiamba")).thenReturn(1);
        when(rentableRepositoryMock.getRentableById(1L)).thenReturn(cottage);
        when(freeTermRepositoryMock.getFreeTermsByType(ReservationType.COTTAGE)).thenReturn(List.of(freeTermMock));
        when(freeTermMock.getType()).thenReturn(ReservationType.COTTAGE);
        when(freeTermMock.getStartTime()).thenReturn(LocalDateTime.parse("2022-05-15T00:00:00"));
        when(freeTermMock.getEndTime()).thenReturn(LocalDateTime.parse("2022-06-28T00:00:00"));
        when(freeTermMock.getEntityName()).thenReturn("Prvenstvo Srbije");
        when(freeTermMock.getOwnerUsername()).thenReturn("Piwneuh");
        when(rentableRepositoryMock.getCottageByNameAndOwner("Prvenstvo Srbije", "Piwneuh")).thenReturn(cottage);
        when(reservationRepositoryMock.getOccupied("Prvenstvo Srbije", "Piwneuh",
                LocalDateTime.parse("2022-06-23T00:00:00"), LocalDateTime.parse("2022-06-26T00:00:00")))
                .thenReturn(null);
        when(reservationRepositoryMock.save(reservation)).thenReturn(reservation);

        Reservation created = reservationService.reserveRentable(1L, reservation);

        assertEquals(reservation.getName(), created.getName());
        assertEquals(reservation.getType(), created.getType());
        assertEquals(reservation.getStartTime(), created.getStartTime());
        assertEquals(reservation.getEndTime(), created.getEndTime());

        verify(rentableRepositoryMock, times(1)).getCottageLock(1L);
        verify(freeTermRepositoryMock, times(1)).getFreeTermsByType(ReservationType.COTTAGE);
        verify(rentableRepositoryMock, times(1)).getCottageByNameAndOwner("Prvenstvo Srbije", "Piwneuh");
        verify(reservationRepositoryMock, atLeastOnce()).getOccupied("Prvenstvo Srbije", "Piwneuh",
                LocalDateTime.parse("2022-06-23T00:00:00"), LocalDateTime.parse("2022-06-26T00:00:00"));
        verify(reservationRepositoryMock, times(1)).save(reservation);
        verifyNoMoreInteractions(penaltyRepositoryMock);
        verifyNoMoreInteractions(rentableRepositoryMock);
        verifyNoMoreInteractions(freeTermRepositoryMock);
        verifyNoMoreInteractions(reservationRepositoryMock);
    }

    //@Test(expected = ObjectOptimisticLockingFailureException.class)
    public void reserveActionOptimisticLockTest() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");// izmenjen ucitan objekat
                //try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                reservationServiceSpy.reserveAction(1L, "imbiamba"); // bacice ObjectOptimisticLockingFailureException

            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(10); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                reservationServiceSpy.reserveAction(1L, "imbiamba");
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    //@Test
    public void reserveRentablePessimisticLockTest() throws Throwable {

        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        reservation.setOwnerUsername("Piwneuh");
        reservation.setName("Silver Mirror");
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
                //try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                Reservation reserved = reservationServiceSpy.reserveRentable(2L, reservation);
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
                try { Thread.sleep(6, 500000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                Reservation notReserved = reservationServiceSpy.reserveRentable(2L, reservation);
                assertNull(notReserved);
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

    //@Test
    public void getCottagePessimisticLockTest() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                //try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                Rentable cottage = reservationServiceSpy.getCottageById(2L);
                assertEquals("Silver Mirror", cottage.getName());
            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(80, 100); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                Rentable cottage = reservationServiceSpy.getCottageById(2L);
                assertNull(cottage);
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
