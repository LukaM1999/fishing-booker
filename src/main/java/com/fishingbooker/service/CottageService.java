package com.fishingbooker.service;

import com.fishingbooker.model.Cottage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CottageService {
    Optional<Cottage> findById(Long id);

    List<Cottage> findAll();

    Cottage registerCottage(Cottage cottage, MultipartFile[] files) throws IOException;

    List<Cottage> findAllByOwnerUsername(String username);

    boolean deleteCottage(Long cottageId);
}
