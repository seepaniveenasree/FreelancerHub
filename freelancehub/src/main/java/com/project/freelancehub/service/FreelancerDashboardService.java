// src/main/java/com/project/freelancehub/service/FreelancerDashboardService.java
package com.project.freelancehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.project.freelancehub.model.Project;
import com.project.freelancehub.repository.ProjectRepository;

@Service
public class FreelancerDashboardService {

    @Autowired
    private ProjectRepository projectRepo;

    public Map<String, Object> getDashboard(String freelancerId) {
        // Fetch all projects to categorize them
        List<Project> allProjects = projectRepo.findAll();

        List<Project> available = new ArrayList<>(); // New category
        List<Project> applied = new ArrayList<>();
        List<Project> pendingAcceptance = new ArrayList<>(); // New category
        List<Project> assigned = new ArrayList<>();
        List<Project> completed = new ArrayList<>();

        for (Project p : allProjects) {
            // 1. Available projects (status "hiring" and public, not applied to by current freelancer)
            if ("hiring".equalsIgnoreCase(p.getStatus()) && "public".equalsIgnoreCase(p.getVisibility())) {
                if (p.getRequests() == null || !p.getRequests().contains(freelancerId)) {
                    available.add(p);
                }
            }

            // 2. Applied projects
            if (p.getRequests() != null && p.getRequests().contains(freelancerId)) {
                applied.add(p);
            }

            // 3. Pending Acceptance projects (assigned to freelancer, but awaiting response)
            if (freelancerId.equals(p.getAssignedFreelancerId()) && "awaiting_freelancer_response".equalsIgnoreCase(p.getStatus())) {
                pendingAcceptance.add(p);
            }

            // 4. Assigned projects
            if (freelancerId.equals(p.getAssignedFreelancerId()) && "assigned".equalsIgnoreCase(p.getStatus())) {
                assigned.add(p);
            }

            // 5. Completed projects
            if (freelancerId.equals(p.getAssignedFreelancerId()) && "completed".equalsIgnoreCase(p.getStatus())) {
                completed.add(p);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("availableProjects", available); // Add to map
        map.put("appliedProjects", applied);
        map.put("pendingAcceptanceProjects", pendingAcceptance); // Add to map
        map.put("assignedProjects", assigned);
        map.put("completedProjects", completed);
        return map;
    }
}