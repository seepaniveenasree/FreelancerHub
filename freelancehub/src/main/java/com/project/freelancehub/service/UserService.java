package com.project.freelancehub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.freelancehub.dto.UserRegistrationRequest;
import com.project.freelancehub.model.FreelancerProfile;
import com.project.freelancehub.model.User;
import com.project.freelancehub.repository.FreelancerProfileRepository;
import com.project.freelancehub.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FreelancerProfileRepository profileRepo;

    public User registerUser(User user, FreelancerProfile profile) {
        User savedUser = userRepo.save(user);

        if ("freelancer".equalsIgnoreCase(user.getRole()) && profile != null) {
            profile.setUserId(savedUser.getId());
            FreelancerProfile savedProfile = profileRepo.save(profile);

            savedUser.setFreelancerProfileId(savedProfile.getId());
            userRepo.save(savedUser);
        }

        return savedUser;
    }

    public User login(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt.get();
        }
        return null;
    }

    public FreelancerProfile getFreelancerProfile(String userId) {
        return profileRepo.findByUserId(userId).orElse(null);
    }

    public User registerUser(UserRegistrationRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole());

        User savedUser = userRepo.save(user);

        if ("freelancer".equalsIgnoreCase(req.getRole())) {
            FreelancerProfile profile = new FreelancerProfile();
            profile.setUserId(savedUser.getId());
            profile.setExperienceLevel(req.getExperienceLevel());
            profile.setCompanyName(req.getCompanyName());
            profile.setSkills(req.getSkills());
            profile.setProjectLinks(req.getProjectLinks());
            profile.setBio(req.getBio());
            profile.setGithubLink(req.getGithubLink());
            profile.setLinkedInLink(req.getLinkedInLink());
            profile.setHourlyRate(req.getHourlyRate());

            FreelancerProfile savedProfile = profileRepo.save(profile);
            savedUser.setFreelancerProfileId(savedProfile.getId());
            userRepo.save(savedUser);
        }

        return savedUser;
    }

    public User updateUser(String userId, User updatedUser) {
        Optional<User> existingUserOpt = userRepo.findById(userId);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            // IMPORTANT: Handle password update carefully. For simplicity here,
            // we're just copying it. In a real app, you'd re-hash if changed.
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());

            return userRepo.save(existingUser);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    public FreelancerProfile updateFreelancerProfile(String userId, FreelancerProfile updatedProfile) {
        Optional<FreelancerProfile> existingProfileOpt = profileRepo.findByUserId(userId);
        if (existingProfileOpt.isPresent()) {
            FreelancerProfile existingProfile = existingProfileOpt.get();

            // Log the incoming updatedProfile's LinkedIn link
            System.out.println("UserService: Incoming updatedProfile LinkedIn Link: " + updatedProfile.getLinkedInLink());

            // Set all fields from the incoming updatedProfile onto the existingProfile
            existingProfile.setExperienceLevel(updatedProfile.getExperienceLevel());
            existingProfile.setCompanyName(updatedProfile.getCompanyName());
            existingProfile.setSkills(updatedProfile.getSkills());
            existingProfile.setProjectLinks(updatedProfile.getProjectLinks());
            existingProfile.setBio(updatedProfile.getBio());
            existingProfile.setGithubLink(updatedProfile.getGithubLink());
            existingProfile.setLinkedInLink(updatedProfile.getLinkedInLink());
            existingProfile.setHourlyRate(updatedProfile.getHourlyRate());

            // Log the existingProfile's LinkedIn link BEFORE saving
            System.out.println("UserService: existingProfile LinkedIn Link BEFORE save: " + existingProfile.getLinkedInLink());

            FreelancerProfile savedProfile = profileRepo.save(existingProfile); // Save the updated existing profile

            // Log the savedProfile's LinkedIn link AFTER saving
            System.out.println("UserService: savedProfile LinkedIn Link AFTER save: " + savedProfile.getLinkedInLink());

            return savedProfile; // Return the saved profile
        }
        throw new RuntimeException("Freelancer profile not found for user ID: " + userId);
    }

    public Optional<User> getUserById(String userId) {
        return userRepo.findById(userId);
    }
}
