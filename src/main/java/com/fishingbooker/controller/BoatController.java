package com.fishingbooker.controller;

import com.fishingbooker.model.Boat;
import com.fishingbooker.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boat")
public class BoatController {

    @Autowired
    private BoatService boatService;

    @GetMapping("/find/{userId}")
    @PreAuthorize("hasAuthority('BOAT_OWNER')")
    public Optional<Boat> loadById(@PathVariable Long userId){
        return this.boatService.findById(userId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('BOAT_OWNER')")
    public List<Boat> getAll(){
        return this.boatService.findAll();
    }

}
