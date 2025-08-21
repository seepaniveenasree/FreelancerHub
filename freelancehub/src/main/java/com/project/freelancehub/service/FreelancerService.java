// src/main/java/com/project/freelancehub/service/FreelancerService.java
package com.project.freelancehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.freelancehub.model.FreelancerProfile;
import com.project.freelancehub.model.User; // Import User model
import com.project.freelancehub.repository.FreelancerProfileRepository;
import com.project.freelancehub.dto.FreelancerWithUserDetailsDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private UserService userService; // <--- Make sure UserService is correctly Autowired

    public List<FreelancerWithUserDetailsDTO> getAllFreelancers() {
        List<FreelancerProfile> profiles = freelancerProfileRepository.findAll();

        return profiles.stream()
                .map(profile -> {
                    // 1. Fetch the associated User object using the userId from the FreelancerProfile
                    Optional<User> userOptional = userService.getUserById(profile.getUserId()); // <--- VERY IMPORTANT: Call the method to get User by ID
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        // 2. Create the DTO, passing the User's name and email
                        return new FreelancerWithUserDetailsDTO(
                                profile,
                                user.getName(), // <--- Pass the User's name here
                                user.getEmail()
                        );
                    } else {
                        // Fallback in case a User isn't found for a profile (e.g., data inconsistency)
                        System.err.println("User not found for FreelancerProfile with userId: " + profile.getUserId());
                        return new FreelancerWithUserDetailsDTO(profile, "Unknown User", "unknown@example.com");
                    }
                })
                .collect(Collectors.toList());
    }
}