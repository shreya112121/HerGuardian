package com.org.controller;

import com.org.model.EmergencyContact;
import com.org.service.EmergencyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class EmergencyContactController {

    @Autowired
    private EmergencyContactService contactService;

   
    @GetMapping("/{userId}")
    public ResponseEntity<List<EmergencyContact>> getContacts(@PathVariable Long userId) {
        return ResponseEntity.ok(contactService.getContactsByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addContact(@RequestBody EmergencyContact contact) {
        Map<String, Object> response = new HashMap<>();
        try {
            EmergencyContact saved = contactService.addContact(contact);
            response.put("success", true);
            response.put("message", "Contact added!");
            response.put("contact", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

  
    @DeleteMapping("/delete/{contactId}")
    public ResponseEntity<Map<String, Object>> deleteContact(@PathVariable Long contactId) {
        Map<String, Object> response = new HashMap<>();
        try {
            contactService.deleteContact(contactId);
            response.put("success", true);
            response.put("message", "Contact deleted!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/count/{userId}")
    public ResponseEntity<Map<String, Object>> countContacts(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("count", contactService.countContacts(userId));
        return ResponseEntity.ok(response);
    }
}