package com.fishingbooker.service;

import com.fishingbooker.model.Penalty;
import com.fishingbooker.repository.PenaltyRepository;
import com.fishingbooker.service.impl.PenaltyServiceImpl;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PenaltyServiceTest {

    @Mock
    private PenaltyRepository penaltyRepositoryMock;

    @InjectMocks
    private PenaltyServiceImpl penaltyService;

    @Test
    public void countPenaltiesByCustomerUsernameTest() {

        when(penaltyRepositoryMock.countPenaltiesByCustomerUsername("imbiamba")).thenReturn(1);
        int count = penaltyService.countPenaltiesByCustomerUsername("imbiamba");
        assertEquals(1, count);

        verify(penaltyRepositoryMock, times(1)).deletePenaltiesBeforeFirstOfMonth(any());
        verify(penaltyRepositoryMock, times(1)).countPenaltiesByCustomerUsername("imbiamba");
        verifyNoMoreInteractions(penaltyRepositoryMock);
    }

    @Test
    public void findPenaltiesByCustomerUsernameTest() {

        when(penaltyRepositoryMock.findPenaltiesByCustomerUsername("imbiamba")).thenReturn(Arrays.asList(new Penalty("imbiamba")));
        List<Penalty> penalties = penaltyService.findPenaltiesByCustomerUsername("imbiamba");
        assertEquals(1, penalties.size());
        assertEquals("imbiamba", penalties.get(0).getCustomerUsername());

        verify(penaltyRepositoryMock, times(1)).deletePenaltiesBeforeFirstOfMonth(any());
        verify(penaltyRepositoryMock, times(1)).findPenaltiesByCustomerUsername("imbiamba");
        verifyNoMoreInteractions(penaltyRepositoryMock);
    }
}
