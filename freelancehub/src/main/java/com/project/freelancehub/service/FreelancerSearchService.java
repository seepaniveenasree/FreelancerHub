package com.project.freelancehub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.freelancehub.model.FreelancerProfile;
import com.project.freelancehub.model.User;
import com.project.freelancehub.repository.FreelancerProfileRepository;
import com.project.freelancehub.repository.UserRepository;
import com.project.freelancehub.dto.FreelancerWithUserDetailsDTO;

@Service
public class FreelancerSearchService {

    @Autowired
    private FreelancerProfileRepository profileRepo;

    @Autowired
    private UserRepository userRepo; // Ensure UserRepository is correctly autowired

    // Helper method to convert FreelancerProfile to FreelancerWithUserDetailsDTO
    private FreelancerWithUserDetailsDTO convertToDto(FreelancerProfile profile) {
        Optional<User> userOptional = userRepo.findById(profile.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new FreelancerWithUserDetailsDTO(profile, user.getName(), user.getEmail());
        } else {
            System.err.println("User not found for FreelancerProfile with userId: " + profile.getUserId());
            return new FreelancerWithUserDetailsDTO(profile, "Unknown User", "unknown@example.com");
        }
    }

    public List<FreelancerWithUserDetailsDTO> searchBySkill(String skill) {
        List<FreelancerProfile> profiles = profileRepo.findBySkillsContainingIgnoreCase(skill);
        return profiles.stream()
                       .map(this::convertToDto)
                       .collect(Collectors.toList());
    }

    public List<FreelancerWithUserDetailsDTO> searchByExperience(String level) {
        List<FreelancerProfile> profiles = profileRepo.findByExperienceLevelContainingIgnoreCase(level);
        return profiles.stream()
                       .map(this::convertToDto)
                       .collect(Collectors.toList());
    }

    // Corrected searchByName method
    public List<FreelancerWithUserDetailsDTO> searchByName(String name) {
        // 1. Find users by name (this method must exist in UserRepository)
        List<User> users = userRepo.findByNameContainingIgnoreCase(name);

        List<FreelancerWithUserDetailsDTO> result = new ArrayList<>();
        for (User user : users) {
            // 2. For each user, find their associated FreelancerProfile
            Optional<FreelancerProfile> profileOptional = profileRepo.findByUserId(user.getId());
            if (profileOptional.isPresent()) {
                FreelancerProfile profile = profileOptional.get();
                // 3. Create DTO using both user and profile data
                result.add(new FreelancerWithUserDetailsDTO(profile, user.getName(), user.getEmail()));
            }
        }
        return result;
    }
}
