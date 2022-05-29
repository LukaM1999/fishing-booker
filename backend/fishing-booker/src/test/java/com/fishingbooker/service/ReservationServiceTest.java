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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private Reservation reservationMock;

    @Mock
    private Rentable rentableMock;

    @Mock
    private FreeTerm freeTermMock;

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

        verify(penaltyRepositoryMock, times(1)).countPenaltiesByCustomerUsername("imbiamba");
        verify(rentableRepositoryMock, times(1)).getRentableById(1L);
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

}
