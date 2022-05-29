package com.fishingbooker.service.impl;

import com.fishingbooker.model.Points;
import com.fishingbooker.model.UserPoints;
import com.fishingbooker.repository.PointsRepository;
import com.fishingbooker.repository.UserPointsRepository;
import com.fishingbooker.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointsServiceImpl implements PointsService {

    @Autowired
    private PointsRepository pointsRepository;
    @Autowired
    private UserPointsRepository userPointsRepository;

    @Override
    public List<Points> getPoints() {
        return pointsRepository.findAll();
    }

    @Override
    public Points updatePoints(Points points) {
        Optional<Points> pointsToUpdate = pointsRepository.findById(1L);
        pointsToUpdate.orElseThrow().setId(1L);
        pointsToUpdate.orElseThrow().setCustomerPoints(points.getCustomerPoints());
        pointsToUpdate.orElseThrow().setOwnerPoints(points.getOwnerPoints());
        pointsToUpdate.orElseThrow().setSystemTax(points.getSystemTax());
        pointsToUpdate.orElseThrow().setSilver(points.getSilver());
        pointsToUpdate.orElseThrow().setGold(points.getGold());
        return pointsRepository.save(points);
    }

    @Override
    public UserPoints getUserPoints(String username) {
        return userPointsRepository.findByUsername(username);
    }

    @Override
    public UserPoints createUserPoints(UserPoints userPoints) {
        return userPointsRepository.save(userPoints);
    }

    @Override
    public UserPoints updateUserPoints(UserPoints userPoints) {
        UserPoints toUpdate = userPointsRepository.findByUsername(userPoints.getUsername());
        toUpdate.setPoints(userPoints.getPoints());
        return userPointsRepository.save(toUpdate);
    }
}

