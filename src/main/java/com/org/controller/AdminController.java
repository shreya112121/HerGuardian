package com.org.controller;

import com.org.model.Complaint;
import com.org.model.SosAlert;
import com.org.model.User;
import com.org.model.WalkHistory;
import com.org.repository.ComplaintRepository;
import com.org.repository.SosAlertRepository;
import com.org.repository.UserRepository;
import com.org.repository.WalkHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private SosAlertRepository sosAlertRepository;

    @Autowired
    private WalkHistoryRepository walkHistoryRepository;

    // Stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalComplaints", complaintRepository.count());
        stats.put("totalSos", sosAlertRepository.count());
        stats.put("totalWalks", walkHistoryRepository.count());
        return ResponseEntity.ok(stats);
    }

   
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintRepository.findAll());
    }

    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<Map<String, Object>> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Map<String, Object> response = new HashMap<>();
        try {
            Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found!"));
            complaint.setStatus(status);
            complaintRepository.save(complaint);
            response.put("success", true);
            response.put("message", "Status updated!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

   
    @GetMapping("/sos")
    public ResponseEntity<List<SosAlert>> getAllSos() {
        return ResponseEntity.ok(sosAlertRepository.findAll());
    }

   
    @GetMapping("/walks")
    public ResponseEntity<List<WalkHistory>> getAllWalks() {
        return ResponseEntity.ok(walkHistoryRepository.findAll());
    }
}