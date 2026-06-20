package com.org.controller;

import com.org.model.SosAlert;
import com.org.service.SosAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sos")
@CrossOrigin(origins = "*")
public class SosAlertController {

    @Autowired
    private SosAlertService sosAlertService;

    @PostMapping("/trigger")
    public ResponseEntity<?> triggerSos(@RequestBody Map<String, Object> request) {
        if (request.get("userId") == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "userId required"
            ));
        }

        Long userId = Long.parseLong(request.get("userId").toString());
        Double lat = request.get("latitude") != null
                ? Double.parseDouble(request.get("latitude").toString()) : null;
        Double lng = request.get("longitude") != null
                ? Double.parseDouble(request.get("longitude").toString()) : null;

        SosAlert alert = sosAlertService.sendSmsToContacts(userId, lat, lng);
        boolean smsSent = "sms_sent".equals(alert.getStatus());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "smsSent", smsSent,
                "sosId", alert.getId(),
                "status", alert.getStatus(),
                "message", smsSent
                        ? "SOS alert sent to your emergency contacts!"
                        : "SOS saved but SMS could not be sent. Status: " + alert.getStatus()
        ));
    }

    @PutMapping("/resolve/{sosId}")
    public ResponseEntity<?> resolveSos(@PathVariable Long sosId,
                                         @RequestBody(required = false) Map<String, Object> request) {
        Long userId = request != null && request.get("userId") != null
                ? Long.parseLong(request.get("userId").toString())
                : null;
        SosAlert resolved = sosAlertService.resolveAlert(sosId, userId);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "sosId", resolved.getId(),
                "status", resolved.getStatus()
        ));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getSosHistory(@RequestParam Long userId) {
        List<SosAlert> history = sosAlertService.getAlertHistory(userId);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "alerts", history
        ));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<?> getSosCount(@PathVariable Long userId) {
        long count = sosAlertService.getSosCount(userId);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", count
        ));
    }
}