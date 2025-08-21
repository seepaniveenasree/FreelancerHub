package com.project.freelancehub.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.freelancehub.model.Project;
import com.project.freelancehub.model.User;
import com.project.freelancehub.model.FreelancerProfile;
import com.project.freelancehub.repository.ProjectRepository;
import com.project.freelancehub.repository.UserRepository;
import com.project.freelancehub.repository.FreelancerProfileRepository;
import com.project.freelancehub.dto.FreelancerWithUserDetailsDTO; // Import the DTO

@Service
public class ClientDashboardService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private FreelancerProfileRepository freelancerProfileRepo;

    public Map<String, Object> getDashboard(String clientId) {
        List<Project> projects = projectRepo.findByClientId(clientId);

        List<Map<String, Object>> hiringProjectsWithApplicants = new ArrayList<>();
        List<Project> assigned = new ArrayList<>();
        List<Project> completed = new ArrayList<>();
        List<Project> pendingFreelancerAcceptanceProjects = new ArrayList<>(); // ⭐ NEW: List for projects awaiting freelancer acceptance ⭐

        for (Project p : projects) {
            if (p.getStatus() != null) {
                switch (p.getStatus().toLowerCase()) {
                    case "hiring":
                        // For hiring projects, fetch applicant details
                        List<FreelancerWithUserDetailsDTO> applicants = new ArrayList<>();
                        if (p.getRequests() != null && !p.getRequests().isEmpty()) {
                            for (String freelancerUserId : p.getRequests()) {
                                Optional<User> userOptional = userRepo.findById(freelancerUserId);
                                if (userOptional.isPresent()) {
                                    User user = userOptional.get();
                                    Optional<FreelancerProfile> profileOptional = freelancerProfileRepo.findByUserId(freelancerUserId);
                                    if (profileOptional.isPresent()) {
                                        FreelancerProfile profile = profileOptional.get();
                                        applicants.add(new FreelancerWithUserDetailsDTO(profile, user.getName(), user.getEmail()));
                                    } else {
                                        System.err.println("ClientDashboardService: FreelancerProfile not found for userId: " + freelancerUserId);
                                        // Still add a DTO, but with partial info if profile is missing
                                        // Create a dummy profile to avoid NullPointerException in DTO constructor
                                        FreelancerProfile dummyProfile = new FreelancerProfile();
                                        dummyProfile.setUserId(freelancerUserId); // Set userId for identification
                                        applicants.add(new FreelancerWithUserDetailsDTO(dummyProfile, user.getName(), user.getEmail()));
                                    }
                                } else {
                                    System.err.println("ClientDashboardService: User not found for userId in project requests: " + freelancerUserId);
                                }
                            }
                        }
                        Map<String, Object> hiringProjectMap = new HashMap<>();
                        hiringProjectMap.put("project", p);
                        hiringProjectMap.put("applicants", applicants);
                        hiringProjectsWithApplicants.add(hiringProjectMap);
                        break;
                    case "assigned":
                        assigned.add(p);
                        break;
                    case "completed":
                        completed.add(p);
                        break;
                    case "pending_acceptance": // ⭐ NEW: Categorize pending acceptance projects separately ⭐
                        pendingFreelancerAcceptanceProjects.add(p);
                        break;
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("hiringProjects", hiringProjectsWithApplicants); // This now contains project + applicants
        map.put("assignedProjects", assigned);
        map.put("completedProjects", completed);
        map.put("pendingFreelancerAcceptanceProjects", pendingFreelancerAcceptanceProjects); // ⭐ Add new list to the map ⭐
        return map;
    }
}
