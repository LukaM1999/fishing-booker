package com.fishingbooker.service;

import com.fishingbooker.model.Adventure;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdventureService {
    Adventure registerAdventure(Adventure readValue, MultipartFile[] files)throws IOException;

    Optional<Adventure> findById(Long userId);

    List<Adventure> findAll();

    List<Adventure> findAllByOwnerUsername(String username);

    boolean deleteCottage(Long adventureId);
}
