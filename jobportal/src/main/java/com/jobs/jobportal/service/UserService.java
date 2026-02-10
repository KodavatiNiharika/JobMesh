package com.jobs.jobportal.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.jobs.jobportal.model.User;
import com.jobs.jobportal.repository.UserRepo;
import com.jobs.jobportal.Security.JwtUtil;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Encrypt
        return userRepo.save(user);
    }

    public User login(User user) {
        User found = userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(user.getPassword(), found.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return found;
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getEmail());
    }
}
