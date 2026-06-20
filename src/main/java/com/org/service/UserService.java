package com.org.service;

import com.org.model.User;
import com.org.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }


    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        throw new RuntimeException("Invalid email or password!");
    }

  
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User updateUser(Long userId, Map<String, String> request) {
        User user = getUserById(userId);
        if (request.get("fullName") != null) user.setFullName(request.get("fullName"));
        if (request.get("phone") != null) user.setPhone(request.get("phone"));
        if (request.get("address") != null) user.setAddress(request.get("address"));
        return userRepository.save(user);
    }
}