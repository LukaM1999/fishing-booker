package com.fishingbooker.service;

import com.fishingbooker.model.Penalty;

import java.time.LocalDateTime;
import java.util.List;

public interface PenaltyService {
    int countPenaltiesByCustomerUsername(String customerUsername);
    List<Penalty> findPenaltiesByCustomerUsername(String customerUsername);
    Penalty createPenalty(Penalty penalty);
}
