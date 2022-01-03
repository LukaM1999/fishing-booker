package com.fishingbooker.controller;

import com.fishingbooker.model.Cottage;
import com.fishingbooker.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cottage")
public class CottageController {

    @Autowired
    private CottageService cottageService;

    @GetMapping("/find/{userId}")
    @PreAuthorize("hasAuthority('COTTAGE_OWNER')")
    public Optional<Cottage> loadById(@PathVariable Long userId){
        return this.cottageService.findById(userId);
    }

    @GetMapping("/all")
    public List<Cottage> getAll(){
        return this.cottageService.findAll();
    }
}
