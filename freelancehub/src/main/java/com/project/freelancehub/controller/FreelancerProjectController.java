package com.project.freelancehub.controller;

import com.project.freelancehub.model.Project;
import com.project.freelancehub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Make sure Map is imported if used in dashboard method

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/freelancer/projects") // THIS MUST BE EXACTLY "/api/freelancer/projects"
public class FreelancerProjectController {

    @Autowired
    private ProjectService projectService;

    // Endpoint for a freelancer to accept an assigned project
    // Path: /api/freelancer/projects/accept/{projectId}/{freelancerId}
    @PutMapping("/accept/{projectId}/{freelancerId}") // THIS MUST BE EXACTLY "/accept/{projectId}/{freelancerId}"
    public ResponseEntity<Project> acceptProject(
            @PathVariable String projectId,
            @PathVariable String freelancerId) {
        try {
            System.out.println("Backend: Attempting to accept project. ProjectId: " + projectId + ", FreelancerId: " + freelancerId); // ADD THIS LOG
            Project updatedProject = projectService.acceptAssignment(projectId, freelancerId);
            System.out.println("Backend: Project accepted successfully. Project ID: " + updatedProject.getId()); // ADD THIS LOG
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (IllegalStateException e) {
            System.err.println("Backend: Accept assignment error: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.err.println("Backend: Error accepting assignment: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Backend: Unexpected error accepting assignment: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for a freelancer to decline an assigned project
    // Path: /api/freelancer/projects/decline/{projectId}/{freelancerId}
    @PutMapping("/decline/{projectId}/{freelancerId}") // THIS MUST BE EXACTLY "/decline/{projectId}/{freelancerId}"
    public ResponseEntity<Project> declineProject(
            @PathVariable String projectId,
            @PathVariable String freelancerId) {
        try {
            System.out.println("Backend: Attempting to decline project. ProjectId: " + projectId + ", FreelancerId: " + freelancerId); // ADD THIS LOG
            Project updatedProject = projectService.declineAssignment(projectId, freelancerId);
            System.out.println("Backend: Project declined successfully. Project ID: " + updatedProject.getId()); // ADD THIS LOG
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (IllegalStateException e) {
            System.err.println("Backend: Decline assignment error: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.err.println("Backend: Error declining assignment: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Backend: Unexpected error declining assignment: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- Include other Freelancer Project specific methods here as well if they exist ---
    // Example: getAvailableProjectsForFreelancers, applyToProject, getFreelancerDashboard, searchProjects
    // Make sure they are also present as per our previous discussions.
    // I am omitting them here for brevity but ensure your full file has them.

    // 1. Endpoint to get all public projects (for freelancers to browse specifically)
    // Path: /api/freelancer/projects/public
    @GetMapping("/public")
    public ResponseEntity<List<Project>> getAvailableProjectsForFreelancers() {
        try {
            List<Project> publicProjects = projectService.getPublicProjects();
            return new ResponseEntity<>(publicProjects, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching public projects for freelancers: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. Endpoint for a freelancer to apply to a project
    // Path: /api/freelancer/projects/apply/{projectId}/{freelancerId}
    @PostMapping("/apply/{projectId}/{freelancerId}")
    public ResponseEntity<Project> applyToProject(
            @PathVariable String projectId,
            @PathVariable String freelancerId) {
        try {
            Project updatedProject = projectService.applyForProject(projectId, freelancerId);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (IllegalStateException e) {
            System.err.println("Application error: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            System.err.println("Error applying to project: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Unexpected error applying to project: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 5. Endpoint for freelancers to view their dashboard data
    // Path: /api/freelancer/projects/dashboard/{freelancerId}
    @GetMapping("/dashboard/{freelancerId}")
    public ResponseEntity<?> getFreelancerDashboard(@PathVariable String freelancerId) {
        try {
            Map<String, Object> dashboardData = projectService.getFreelancerDashboardData(freelancerId);
            return new ResponseEntity<>(dashboardData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching freelancer dashboard: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 6. Endpoint to search projects by title or skills (for freelancers)
    // Path: /api/freelancer/projects/search/{type}/{query}
    @GetMapping("/search/{type}/{query}")
    public ResponseEntity<?> searchProjects(@PathVariable String type, @PathVariable String query) {
        try {
            List<Project> foundProjects;
            if ("title".equalsIgnoreCase(type)) {
                foundProjects = projectService.searchProjectsByTitle(query);
            } else if ("skills".equalsIgnoreCase(type)) {
                foundProjects = projectService.searchProjectsBySkills(query);
            } else {
                return new ResponseEntity<>("Invalid search type. Must be 'title' or 'skills'.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(foundProjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error searching projects: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}