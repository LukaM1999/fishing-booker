package com.fishingbooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishingbooker.model.Adventure;
import com.fishingbooker.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/adventure")
public class AdventureController {

    @Autowired
    private AdventureService adventureService;

    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public Adventure registerAdventure(@RequestParam("adventure") MultipartFile adventure, @RequestParam("files") MultipartFile[] files) throws IOException {
        return adventureService.registerAdventure(new ObjectMapper().readValue(adventure.getBytes(), Adventure.class), files);
    }
}
