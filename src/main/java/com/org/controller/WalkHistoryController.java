package com.org.controller;

import com.org.model.WalkHistory;
import com.org.service.WalkHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/walk")
@CrossOrigin(origins = "*")
public class WalkHistoryController {

    @Autowired
    private WalkHistoryService walkService;

  
    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startWalk(@RequestBody WalkHistory walk) {
        Map<String, Object> response = new HashMap<>();
        try {
            WalkHistory saved = walkService.startWalk(walk);
            response.put("success", true);
            response.put("walkId", saved.getId());
            response.put("message", "Walk started!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/end/{walkId}")
    public ResponseEntity<Map<String, Object>> endWalk(
            @PathVariable Long walkId,
            @RequestParam String status) {
        Map<String, Object> response = new HashMap<>();
        try {
            walkService.endWalk(walkId, status);
            response.put("success", true);
            response.put("message", "Walk ended!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

  
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<WalkHistory>> getWalkHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(walkService.getWalksByUserId(userId));
    }

 
    @GetMapping("/count/{userId}")
    public ResponseEntity<Map<String, Object>> countWalks(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("total", walkService.countWalks(userId));
        response.put("safe", walkService.countSafeWalks(userId));
        return ResponseEntity.ok(response);
    }
}