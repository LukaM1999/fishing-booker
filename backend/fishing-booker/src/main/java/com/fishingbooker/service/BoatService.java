package com.fishingbooker.service;

import com.fishingbooker.model.Boat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BoatService {

    public Optional<Boat> findById(Long id);

    public List<Boat> findAll();

    Boat registerBoat(Boat readValue, MultipartFile[] files) throws IOException;

    List<Boat> findAllByOwnerUsername(String username);

    boolean deleteBoat(Long boatId);
}
