package com.fishingbooker.service;

import com.fishingbooker.model.Boat;

import java.util.List;
import java.util.Optional;

public interface BoatService {

    public Optional<Boat> findById(Long id);

    public List<Boat> findAll();

}
