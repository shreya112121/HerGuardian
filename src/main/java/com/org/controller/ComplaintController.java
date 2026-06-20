package com.org.controller;

import com.org.model.Complaint;
import com.org.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaint")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

   
    @PostMapping("/file")
    public ResponseEntity<Map<String, Object>> fileComplaint(@RequestBody Complaint complaint) {
        Map<String, Object> response = new HashMap<>();
        try {
            Complaint saved = complaintService.fileComplaint(complaint);
            response.put("success", true);
            response.put("complaintId", saved.getId());
            response.put("message", "Complaint filed successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

  
    @GetMapping("/{userId}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable Long userId) {
        return ResponseEntity.ok(complaintService.getComplaintsByUserId(userId));
    }
}