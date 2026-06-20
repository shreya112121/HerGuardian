package com.org.service;

import com.org.model.Complaint;
import com.org.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint fileComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

 
    public List<Complaint> getComplaintsByUserId(Long userId) {
        return complaintRepository.findByUserIdOrderByFiledAtDesc(userId);
    }
}