package com.fishingbooker.service.impl;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.repository.CottageRepository;
import com.fishingbooker.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService{

    @Autowired
    private CottageRepository cottageRepository;

    @Override
    public Optional<Cottage> findById(Long id) {
        return cottageRepository.findById(id);
    }

    @Override
    public List<Cottage> findAll() {
        return cottageRepository.findAll();
    }
}
