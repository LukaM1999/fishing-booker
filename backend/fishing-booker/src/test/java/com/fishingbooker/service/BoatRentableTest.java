package com.fishingbooker.service;

import com.fishingbooker.model.Boat;
import com.fishingbooker.repository.BoatRepository;
import com.fishingbooker.service.impl.BoatServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoatRentableTest {

    @InjectMocks
    private BoatServiceImpl boatServiceSpy;

    @Mock
    private BoatRepository boatRepositoryMock;

    @Test
    public void registerBoatTest() throws IOException {
        Boat boat = new Boat();
        boat.setName("Water Boofaloo");
        boat.setPrice(90);
        boat.setAdditionalServices("voda=5;");
        boat.setOwnerUsername("Popaye");
        boat.setAddress("Kralja Petra I");
        boat.setCity("Backa Palanka");
        boat.setImages("");
        MockMultipartFile[] files = new MockMultipartFile[1];
        for (int i = 0; i < files.length; i++) {
            files[i] = new MockMultipartFile("blob", "blob", "image/jpeg", "Hello World!".getBytes());
        }

        when(boatRepositoryMock.save(any(Boat.class))).thenReturn(boat);

        Boat created = boatServiceSpy.registerBoat(boat, files);
        assertEquals(boat.getName(), created.getName());

        verify(boatRepositoryMock, times(2)).save(any(Boat.class));
        verifyNoMoreInteractions(boatRepositoryMock);
    }

    @Test
    public void registerBoatNoImagesTest() throws IOException {
        Boat boat = new Boat();
        boat.setName("Water Boofaloo");
        boat.setPrice(90);
        boat.setAdditionalServices("voda=5;");
        boat.setOwnerUsername("Popaye");
        boat.setAddress("Kralja Petra I");
        boat.setCity("Backa Palanka");
        boat.setImages("");
        MockMultipartFile[] files = new MockMultipartFile[0];

        when(boatRepositoryMock.save(any(Boat.class))).thenReturn(boat);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            boatServiceSpy.registerBoat(boat, files);
        });

        verify(boatRepositoryMock, times(1)).save(any(Boat.class));
        verifyNoMoreInteractions(boatRepositoryMock);
    }

    @Test
    public void deleteBoatTest() {
        Long boatId = 4L;

        when(boatRepositoryMock.findById(boatId)).thenReturn(Optional.of(new Boat()));

        assertTrue(boatServiceSpy.deleteBoat(boatId));

        verify(boatRepositoryMock, times(1)).findById(any(Long.class));
        verify(boatRepositoryMock, times(1)).deleteById(any(Long.class));
        verifyNoMoreInteractions(boatRepositoryMock);
    }

    @Test
    public void getAllOwnerBoats(){
        String ownerUsername = "Popaye";
        when(boatRepositoryMock.findAllByOwnerUsername(ownerUsername)).thenReturn(new ArrayList<>());
        assertNotNull(boatServiceSpy.findAllByOwnerUsername(ownerUsername));
        verify(boatRepositoryMock, times(1)).findAllByOwnerUsername(any(String.class));
        verifyNoMoreInteractions(boatRepositoryMock);
    }

}