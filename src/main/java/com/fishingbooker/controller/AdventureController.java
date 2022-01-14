package com.fishingbooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishingbooker.model.Adventure;
import com.fishingbooker.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adventure")
public class AdventureController {

    @Autowired
    private AdventureService adventureService;

    @GetMapping("/find/{userId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public Optional<Adventure> loadById(@PathVariable Long userId) {
        return this.adventureService.findById(userId);
    }

    @GetMapping("/all")
    public List<Adventure> getAll() {
        return this.adventureService.findAll();
    }

    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public Adventure registerAdventure(@RequestParam("adventure") MultipartFile adventure, @RequestParam("files") MultipartFile[] files) throws IOException {
        return adventureService.registerAdventure(new ObjectMapper().readValue(adventure.getBytes(), Adventure.class), files);
    }
    @GetMapping(value = "/owner")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public List<Adventure> getAllByOwnerUsername(@RequestParam String username){ return this.adventureService.findAllByOwnerUsername(username);}

    @DeleteMapping( "/delete/{adventureId}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR', 'ADMIN')")
    public boolean deleteAdventure(@PathVariable Long adventureId){ return this.adventureService.deleteCottage(adventureId); }
}
