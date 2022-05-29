package com.fishingbooker.service;

import com.fishingbooker.model.Points;
import com.fishingbooker.model.UserPoints;

import java.util.List;

public interface PointsService {

    List<Points> getPoints();

    Points updatePoints(Points points);

    UserPoints getUserPoints(String username);
    UserPoints createUserPoints(UserPoints userPoints);
    UserPoints updateUserPoints(UserPoints userPoints);

}
