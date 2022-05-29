package com.fishingbooker.controller;

import com.fishingbooker.model.Points;
import com.fishingbooker.model.UserPoints;
import com.fishingbooker.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/loyalty", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoyaltyController {
    @Autowired
    private PointsService pointsService;

    @GetMapping("/getPoints")
    public List<Points> getPoints() {
        return pointsService.getPoints();
    }

    @GetMapping("/getUserPoints/{userId}")
    public UserPoints getUserPoints(@PathVariable String userId) {
        return pointsService.getUserPoints(userId);
    }

    @PutMapping("/updatePoints")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Points updatePoints(@RequestBody Points points) {
        return pointsService.updatePoints(points);
    }

}
