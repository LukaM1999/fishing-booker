package com.fishingbooker.service.impl;

import com.fishingbooker.model.Complaint;
import com.fishingbooker.model.Penalty;
import com.fishingbooker.repository.ComplaintRepository;
import com.fishingbooker.service.ComplaintService;
import com.fishingbooker.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PenaltyService penaltyService;

    @Override
    public Complaint createComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> findAllByIssuerUsername(String username) {
        return complaintRepository.findAllByIssuerUsername(username);
    }

    @Override
    public List<Complaint> findAll() {
        List<Complaint> complaints = complaintRepository.findAll();
        ArrayList<Complaint> notReviewed = new ArrayList<>();

        for (Complaint complaint : complaints) {
            if (complaint.isReviewed()) continue;
            if (complaint.isFromCustomer()) notReviewed.add(complaint);
            else if (complaint.isForPenalty()) notReviewed.add(complaint);
        }
        return notReviewed;
    }

    @Override
    public List<Complaint> getById(Long id) {
        return complaintRepository.findAllByReservationId(id);
    }

    @Transactional
    @Override
    public Complaint updateComplaint(Complaint complaint) {
        try {
            Complaint updatedComplaint = complaintRepository.findByIdLocked(complaint.getId());
            if(updatedComplaint == null) return null;
            updatedComplaint.setReviewed(true);
            if(updatedComplaint.isForPenalty())
                penaltyService.createPenalty(new Penalty(complaint.getSubjectUsername()));

            return complaintRepository.save(updatedComplaint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Complaint getCustomerComplaint(Long reservationId) {
        return complaintRepository.getCustomerComplaint(reservationId);
    }
}
