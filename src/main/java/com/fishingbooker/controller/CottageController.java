package com.fishingbooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishingbooker.model.Cottage;
import com.fishingbooker.service.CottageService;
import com.fishingbooker.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cottage")
public class CottageController {

    @Autowired
    private CottageService cottageService;

    @GetMapping("/find/{userId}")
    @PreAuthorize("hasAuthority('COTTAGE_OWNER')")
    public Optional<Cottage> loadById(@PathVariable Long userId) {
        return this.cottageService.findById(userId);
    }

    @GetMapping("/all")
    public List<Cottage> getAll() {
        return this.cottageService.findAll();
    }

    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('COTTAGE_OWNER')")
    public Cottage registerCottage(@RequestParam("cottage") MultipartFile cottage, @RequestParam("files") MultipartFile[] files) throws IOException {
        return cottageService.registerCottage(new ObjectMapper().readValue(cottage.getBytes(), Cottage.class), files);
    }
}