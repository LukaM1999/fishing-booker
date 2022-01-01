package com.fishingbooker.service;

import com.fishingbooker.model.Cottage;

import java.util.List;
import java.util.Optional;

public interface CottageService {
    public Optional<Cottage> findById(Long id);

    public List<Cottage> findAll();
}
