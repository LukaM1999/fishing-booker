package com.fishingbooker.controller;

import com.fishingbooker.model.Instructor;
import com.fishingbooker.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/instructor", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping("/all")
    public List<Instructor> getAll(){
        return this.instructorService.findAll();
    }
}
