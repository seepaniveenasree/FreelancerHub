package com.project.freelancehub.controller;

import com.project.freelancehub.model.Project;
import com.project.freelancehub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/projects") // Base path for general project actions (client and public)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Endpoint for clients to post a new project
    // Path: /api/projects/post
    @PostMapping("/post")
    public ResponseEntity<?> postProject(@RequestBody Project project) {
        try {
            Project newProject = projectService.postProject(project);
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error posting project: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for a client to assign a project to a freelancer
    // Path: /api/projects/assign/{projectId}/{freelancerId}
    @PutMapping("/assign/{projectId}/{freelancerId}")
    public ResponseEntity<?> assignProject(@PathVariable String projectId, @PathVariable String freelancerId) {
        try {
            Project updatedProject = projectService.assignFreelancer(projectId, freelancerId);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error assigning project: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for a client to undo an assignment (project goes back to 'hiring' status)
    // Path: /api/projects/undo-assignment/{projectId}
    @PutMapping("/undo-assignment/{projectId}")
    public ResponseEntity<?> undoAssignment(@PathVariable String projectId) {
        try {
            Project updatedProject = projectService.undoAssignment(projectId);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error undoing assignment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for clients to view their dashboard data
    // Path: /api/projects/dashboard/client/{clientId}
    @GetMapping("/dashboard/client/{clientId}")
    public ResponseEntity<?> getClientDashboard(@PathVariable String clientId) {
        try {
            Map<String, Object> dashboardData = projectService.getClientDashboardData(clientId);
            return new ResponseEntity<>(dashboardData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching client dashboard: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all public projects (general public view, not specific to freelancer browse)
    // Path: /api/projects/public
    @GetMapping("/public")
    public ResponseEntity<?> getPublicProjects() {
        try {
            List<Project> publicProjects = projectService.getPublicProjects();
            return new ResponseEntity<>(publicProjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching public projects: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------- ALL DUPLICATED/CONFLICTING ENDPOINTS REMOVED BELOW -----------
    // The following were removed because they are either handled by FreelancerProjectController
    // or are better placed there semantically.

    // Removed: @PostMapping("/apply/{projectId}/{freelancerId}") (Moved to FreelancerProjectController)
    // Removed: @PutMapping("/freelancer/projects/{projectId}/accept/{freelancerId}") (Moved to FreelancerProjectController)
    // Removed: @PutMapping("/freelancer/projects/{projectId}/decline/{freelancerId}") (Moved to FreelancerProjectController)
    // Removed: @GetMapping("/dashboard/freelancer/{freelancerId}") (Moved to FreelancerProjectController)
    // Removed: @GetMapping("/search/{type}/{query}") (Moved to FreelancerProjectController)
}