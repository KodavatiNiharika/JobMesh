package com.jobs.jobportal.controller;

import com.jobs.jobportal.model.User;
import com.jobs.jobportal.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        User saved = userService.signup(user);
        String token = userService.generateToken(saved);

        return Map.of(
            "token", token,
            "userId", saved.getId(),
            "username", saved.getUserName()
        );
    }


    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        User found = userService.login(user);
        String token = userService.generateToken(found);

        return Map.of(
            "token", token,
            "userId", found.getId(),
            "username", found.getUserName()
        );
    }
}
