package com.project.freelancehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.freelancehub.dto.UserRegistrationRequest;
import com.project.freelancehub.model.FreelancerProfile;
import com.project.freelancehub.model.LoginRequest;
import com.project.freelancehub.model.User;
import com.project.freelancehub.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }

    @GetMapping("/freelancer/profile/{userId}")
    public FreelancerProfile getFreelancerProfile(@PathVariable String userId) {
        return userService.getFreelancerProfile(userId);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }

    // Ensure this method signature is correct and matches the incoming JSON structure
    @PutMapping("/freelancer/profile/{userId}")
    public FreelancerProfile updateFreelancerProfile(@PathVariable String userId, @RequestBody FreelancerProfile updatedProfile) {
        return userService.updateFreelancerProfile(userId, updatedProfile);
    }
}
