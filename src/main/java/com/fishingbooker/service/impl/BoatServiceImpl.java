package com.fishingbooker.service.impl;

import com.fishingbooker.model.Boat;
import com.fishingbooker.repository.BoatRepository;
import com.fishingbooker.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatServiceImpl implements BoatService{

    @Autowired
    private BoatRepository boatRepository;

    @Override
    public Optional<Boat> findById(Long id) {
        return boatRepository.findById(id);
    }

    @Override
    public List<Boat> findAll() {
        return boatRepository.findAll();
    }
}
