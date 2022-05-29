package com.fishingbooker.service;

import com.fishingbooker.model.SaleSubscription;
import com.fishingbooker.repository.SaleSubscriptionRepository;
import com.fishingbooker.service.impl.SaleSubscriptionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SaleSubscriptionServiceTest {

    @Mock
    private SaleSubscriptionRepository saleSubscriptionRepositoryMock;

    @InjectMocks
    private SaleSubscriptionServiceImpl saleSubscriptionService;

    @Mock
    private SaleSubscription saleSubscriptionMock;

    @Test
    public void getCustomerSubscriptionsTest() {
        when(saleSubscriptionRepositoryMock.findByCustomerUsername("imbiamba")).thenReturn(List.of(saleSubscriptionMock));
        List<SaleSubscription> saleSubscriptions = saleSubscriptionService.getCustomerSubscriptions("imbiamba");

        assertEquals(1, saleSubscriptions.size());
        assertEquals(Optional.of(saleSubscriptionMock), Optional.of(saleSubscriptions.get(0)));

        verify(saleSubscriptionRepositoryMock, times(1)).findByCustomerUsername("imbiamba");
        verifyNoMoreInteractions(saleSubscriptionRepositoryMock);
    }

    @Test
    public void createSaleSubscriptionTest() {
        SaleSubscription saleSubscription = new SaleSubscription();
        saleSubscription.setCustomerUsername("imbiamba");
        saleSubscription.setEntityName("Prvenstvo Srbije");
        saleSubscription.setOwnerUsername("Piwneuh");
        when(saleSubscriptionRepositoryMock.save(saleSubscription)).thenReturn(null);
        SaleSubscription created = saleSubscriptionService.createSubscription(saleSubscription);

        assertNull(created);

        verify(saleSubscriptionRepositoryMock, times(1)).
                findByEntityNameAndOwnerUsernameAndCustomerUsername("Prvenstvo Srbije",
                        "Piwneuh", "imbiamba");
        verify(saleSubscriptionRepositoryMock, times(1)).save(saleSubscription);
        verifyNoMoreInteractions(saleSubscriptionRepositoryMock);
    }


}
