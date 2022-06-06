package com.fishingbooker.service;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.*;
import com.fishingbooker.service.impl.PenaltyServiceImpl;
import com.fishingbooker.service.impl.PointsServiceImpl;
import com.fishingbooker.service.impl.ReservationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoyaltyTest {

    @Mock
    private UserPointsRepository userPointsRepositoryMock;

    @Mock
    private PointsRepository pointsRepositoryMock;

    @Mock
    private PointsServiceImpl pointsServiceMock;

    @InjectMocks
    private ReservationServiceImpl reservationServiceMock;

    @Test
    public void updatePointsUserNullTest(){
        when(pointsServiceMock.getUserPoints("imbiamba"))
                .thenReturn(null);
        when(pointsServiceMock.getUserPoints("Fishdude"))
                .thenReturn(new UserPoints("Fishdude", 0));
        when(pointsServiceMock.getPoints()).thenReturn(Arrays.asList(
                new Points())
        );
        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        reservation.setOwnerUsername("Fishdude");
        reservationServiceMock.updatePoints(reservation);
        assertEquals(null, pointsServiceMock.getUserPoints("imbiamba"));
    }

    @Test
    public void updatePointsTest(){
        when(pointsServiceMock.getUserPoints("imbiamba"))
                .thenReturn(new UserPoints("imbiamba", 0));
        when(pointsServiceMock.getUserPoints("Fishdude"))
                .thenReturn(new UserPoints("Fishdude", 0));
        when(pointsServiceMock.getPoints()).thenReturn(List.of(
                new Points(15, 15, 7, 500, 800))
        );
        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        reservation.setOwnerUsername("Fishdude");

        reservationServiceMock.updatePoints(reservation);
        assertEquals(15, pointsServiceMock.getUserPoints("imbiamba").getPoints());
        assertEquals(15, pointsServiceMock.getUserPoints("Fishdude").getPoints());
    }

    @Test
    public void checkForCustomerEmptyListSaleTest(){
        when(pointsServiceMock.getUserPoints("imbiamba"))
                .thenReturn(new UserPoints("imbiamba", 0));
        when(pointsServiceMock.getPoints()).thenReturn(new ArrayList());
        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        int sale = reservationServiceMock.checkForCustomerSale(reservation);
        assertEquals(0, sale);
    }

    @Test
    public void checkForCustomerSaleTest(){
        when(pointsServiceMock.getUserPoints("imbiamba"))
                .thenReturn(new UserPoints("imbiamba", 550));
        when(pointsServiceMock.getPoints()).thenReturn(List.of(
                        new Points(15, 15, 7, 500, 800)));
        Reservation reservation = new Reservation();
        reservation.setCustomerUsername("imbiamba");
        int sale = reservationServiceMock.checkForCustomerSale(reservation);
        assertEquals(10, sale);
    }
}
