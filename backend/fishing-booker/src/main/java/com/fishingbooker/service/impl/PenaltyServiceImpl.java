package com.fishingbooker.service.impl;

import com.fishingbooker.model.Penalty;
import com.fishingbooker.repository.PenaltyRepository;
import com.fishingbooker.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PenaltyServiceImpl implements PenaltyService {

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Override
    public int countPenaltiesByCustomerUsername(String customerUsername) {
        penaltyRepository.deletePenaltiesBeforeFirstOfMonth(LocalDateTime.now().withDayOfMonth(1));
        return penaltyRepository.countPenaltiesByCustomerUsername(customerUsername);
    }

    @Override
    public List<Penalty> findPenaltiesByCustomerUsername(String customerUsername) {
        penaltyRepository.deletePenaltiesBeforeFirstOfMonth(LocalDateTime.now().withDayOfMonth(1));
        return penaltyRepository.findPenaltiesByCustomerUsername(customerUsername);
    }

    @Override
    public Penalty createPenalty(Penalty penalty) {
        penalty.setIssuedAt(LocalDateTime.now());
        return penaltyRepository.save(penalty);
    }
}
