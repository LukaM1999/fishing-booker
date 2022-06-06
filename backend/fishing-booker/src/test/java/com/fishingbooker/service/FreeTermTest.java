package com.fishingbooker.service;

import com.fishingbooker.dto.CustomerReservationDTO;
import com.fishingbooker.model.*;
import com.fishingbooker.repository.FreeTermRepository;
import com.fishingbooker.repository.PenaltyRepository;
import com.fishingbooker.repository.RentableRepository;
import com.fishingbooker.repository.ReservationRepository;
import com.fishingbooker.service.impl.PenaltyServiceImpl;
import com.fishingbooker.service.impl.ReservationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

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
public class FreeTermTest {

    @Mock
    private FreeTermRepository freeTermRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Mock
    private RentableRepository rentableRepositoryMock;

    @InjectMocks
    private ReservationServiceImpl freeTermService;

    @Test
    public void getFreeRentablesTest() {
        when(freeTermRepositoryMock.getFreeTermsByType(ReservationType.ADVENTURE))
                .thenReturn(Arrays.asList(
                        new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                                LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 6, 24, 12, 0))));
        when(reservationRepositoryMock.getOccupiedAdventure("Fishdude", LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 6, 24, 12, 0))).thenReturn(null);
        when(rentableRepositoryMock.getAdventuresByOwner("Fishdude"))
                .thenReturn(Arrays.asList(
                        new Adventure(10L, "Silver Mirror Tour", "Serbia", "Novi Sad", "Bulevar", "Nice", 16, "Nothing", 30, "", 5, "Bio", "Hooks", "", "Fishdude")));

        List<Rentable> freeTerms = freeTermService.getFreeRentables(
                new CustomerReservationDTO(ReservationType.ADVENTURE,
                        LocalDateTime.of(2022, 6, 21, 12, 0),
                        LocalDateTime.of(2022, 6, 24, 12, 0), 2));

        assertEquals(1, freeTerms.size());
        assertEquals("Silver Mirror Tour", freeTerms.get(0).getName());

        verify(freeTermRepositoryMock, times(1)).getFreeTermsByType(ReservationType.ADVENTURE);
        verify(rentableRepositoryMock, times(1)).getAdventuresByOwner("Fishdude");
    }

    @Test
    public void getFreeRentablesFailedTest() {
        when(freeTermRepositoryMock.getFreeTermsByType(ReservationType.ADVENTURE))
                .thenReturn(Arrays.asList(
                        new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                                LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 6, 24, 12, 0))));

        when(reservationRepositoryMock.getOccupiedAdventure("Fishdude", LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 6, 24, 12, 0)))
                .thenReturn(new Reservation());

        when(rentableRepositoryMock.getAdventuresByOwner("Fishdude"))
                .thenReturn(Arrays.asList(
                        new Adventure(10L, "Silver Mirror Tour", "Serbia", "Novi Sad", "Bulevar", "Nice", 16, "Nothing", 30, "", 5, "Bio", "Hooks", "", "Fishdude")));

        List<Rentable> freeTerms = freeTermService.getFreeRentables(
                new CustomerReservationDTO(ReservationType.ADVENTURE,
                        LocalDateTime.of(2022, 6, 21, 12, 0),
                        LocalDateTime.of(2022, 6, 24, 12, 0), 2));

        assertEquals(0, freeTerms.size());

        verify(freeTermRepositoryMock, times(1)).getFreeTermsByType(ReservationType.ADVENTURE);
        verify(rentableRepositoryMock, times(1)).getAdventuresByOwner("Fishdude");
    }

    @Test
    public void createFreeTermNoOverlapTest() {
        FreeTerm freeTerm = new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 6, 24, 12, 0));
        when(freeTermRepositoryMock.getFreeTermsByUsername("Fishdude"))
                .thenReturn(List.of(
                                new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                                        LocalDateTime.of(2022, 7, 21, 12, 0), LocalDateTime.of(2022, 7, 24, 12, 0)),
                        new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                       LocalDateTime.of(2022, 5, 21, 12, 0), LocalDateTime.of(2022, 5, 24, 12, 0)
                       )));

        FreeTerm result = freeTermService.createFreeTerm(freeTerm);

        assertEquals(freeTerm, result);

        verify(freeTermRepositoryMock, times(1)).getFreeTermsByUsername("Fishdude");
    }

    @Test
    public void createFreeTermIfListEmptyTest() {
        FreeTerm freeTerm = new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 6, 24, 12, 0));
        when(freeTermRepositoryMock.getFreeTermsByUsername("Fishdude"))
                .thenReturn(new ArrayList<>());

        FreeTerm result = freeTermService.createFreeTerm(freeTerm);

        assertEquals(freeTerm, result);

        verify(freeTermRepositoryMock, times(1)).getFreeTermsByUsername("Fishdude");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createFreeTermOverlapTest() {
        FreeTerm freeTerm = new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 6, 24, 12, 0));
        when(freeTermRepositoryMock.getFreeTermsByUsername("Fishdude"))
                .thenReturn(List.of(
                        new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                                LocalDateTime.of(2022, 6, 21, 12, 0), LocalDateTime.of(2022, 7, 24, 12, 0)),
                        new FreeTerm(10L, ReservationType.ADVENTURE, "Silver Mirror Tour", "Fishdude",
                                LocalDateTime.of(2022, 5, 21, 12, 0), LocalDateTime.of(2022, 5, 24, 12, 0)
                        )));

        FreeTerm result = freeTermService.createFreeTerm(freeTerm);
    }
}