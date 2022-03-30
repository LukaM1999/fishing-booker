package com.fishingbooker.service.impl;

import com.fishingbooker.model.Instructor;
import com.fishingbooker.repository.InstructorRepository;
import com.fishingbooker.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

}
