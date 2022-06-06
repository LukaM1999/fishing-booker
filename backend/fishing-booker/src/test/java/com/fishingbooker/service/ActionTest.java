package com.fishingbooker.service;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.model.Reservation;
import com.fishingbooker.model.ReservationType;
import com.fishingbooker.repository.FreeTermRepository;
import com.fishingbooker.repository.RentableRepository;
import com.fishingbooker.service.impl.ReservationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ActionTest {

    @Mock
    private RentableRepository rentableRepositoryMock;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    public void createActionTest() {
        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("");
        reservation.setOwnerUsername("Piwneuh");
        reservation.setName("Prvenstvo Srbije");
        reservation.setType(ReservationType.COTTAGE);
        reservation.setStartTime(LocalDateTime.parse("2022-05-15T00:00:00"));
        reservation.setEndTime(LocalDateTime.parse("2022-05-18T00:00:00"));
        reservation.setPrice(90);
        reservation.setGuests(1);
        reservation.setAdditionalServices("");

        Cottage cottage = new Cottage();
        cottage.setName("Prvenstvo Srbije");
        cottage.setOwnerUsername("Piwneuh");
        cottage.setPrice(30);


        when(rentableRepositoryMock.getRentableById(1L)).thenReturn(any(Cottage.class));

        Reservation created = reservationService.createAction(1L, reservation);

        assertEquals(reservation.getName(), cottage.getName());
        assertEquals(reservation.getOwnerUsername(), cottage.getOwnerUsername());
        assertEquals(reservation.getCustomerUsername(), "");
    }
    
}
