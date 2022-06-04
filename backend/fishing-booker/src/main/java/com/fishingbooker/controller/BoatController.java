package com.fishingbooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishingbooker.model.Boat;
import com.fishingbooker.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public List<Boat> getAll(){
        return this.boatService.findAll();
    }

    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('BOAT_OWNER')")
    public Boat registerBoat(@RequestParam("boat") MultipartFile boat, @RequestParam("files") MultipartFile[] files) throws IOException {
        return boatService.registerBoat(new ObjectMapper().readValue(boat.getBytes(), Boat.class), files);
    }

    @GetMapping(value = "/owner")
    @PreAuthorize("hasAuthority('BOAT_OWNER')")
    public List<Boat> getAllByOwnerUsername(@RequestParam String username){ return this.boatService.findAllByOwnerUsername(username);}

    @DeleteMapping( "/delete/{boatId}")
    @PreAuthorize("hasAnyAuthority('BOAT_OWNER', 'ADMIN')")
    public boolean deleteBoat(@PathVariable Long boatId){ return this.boatService.deleteBoat(boatId); }
}
