package com.org.controller;

import com.org.model.User;
import com.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

 
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = new User();
            user.setFullName(request.get("fullName"));
            user.setEmail(request.get("email"));
            user.setPhone(request.get("phone"));
            user.setPassword(request.get("password"));
            userService.registerUser(user);
            response.put("success", true);
            response.put("message", "Registration successful!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.loginUser(
                request.get("email"),
                request.get("password")
            );
            response.put("success", true);
            response.put("message", "Login successful!");
            response.put("userId", user.getId());
            response.put("fullName", user.getFullName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

 
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getProfile(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.getUserById(userId);
            response.put("success", true);
            response.put("fullName", user.getFullName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone());
            response.put("address", user.getAddress());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

  
    @PutMapping("/profile/update/{userId}")
    public ResponseEntity<Map<String, Object>> updateProfile(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.updateUser(userId, request);
            response.put("success", true);
            response.put("message", "Profile updated!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}