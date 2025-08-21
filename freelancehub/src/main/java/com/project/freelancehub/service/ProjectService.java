package com.project.freelancehub.service;

import com.project.freelancehub.model.Project;
import com.project.freelancehub.model.User;
import com.project.freelancehub.model.FreelancerProfile; // Import FreelancerProfile
import com.project.freelancehub.repository.ProjectRepository;
import com.project.freelancehub.repository.UserRepository;
import com.project.freelancehub.repository.FreelancerProfileRepository; // Import FreelancerProfileRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Corrected import for @Transactional

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Objects; // Added import for Objects

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository; // Autowire FreelancerProfileRepository

    /**
     * Posts a new project. Sets default status to "hiring" and visibility to "public" if not provided.
     * Initializes the requests list for applicants.
     * @param project The project object to be saved.
     * @return The saved Project object.
     */
    @Transactional
    public Project postProject(Project project) {
        if (project.getStatus() == null || project.getStatus().isEmpty()) {
            project.setStatus("hiring"); // Default status
        }
        if (project.getVisibility() == null || project.getVisibility().isEmpty()) {
            project.setVisibility("public"); // Default visibility
        }
        if (project.getRequests() == null) {
            project.setRequests(new ArrayList<>()); // Initialize requests list if null
        }
        return projectRepository.save(project);
    }

    /**
     * Allows a freelancer to apply to a public project that is in "hiring" status.
     * @param projectId The ID of the project to apply to.
     * @param freelancerId The ID of the freelancer applying.
     * @return The updated Project object after application.
     * @throws RuntimeException if project or freelancer not found, or project not available for applications.
     * @throws IllegalStateException if the freelancer has already applied to this project.
     */
    @Transactional
    public Project applyForProject(String projectId, String freelancerId) { // Changed return type to Project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found with ID: " + freelancerId));

        if (!"freelancer".equalsIgnoreCase(freelancer.getRole())) {
            throw new RuntimeException("Only freelancers can apply to projects.");
        }

        if (!"hiring".equalsIgnoreCase(project.getStatus()) || !"public".equalsIgnoreCase(project.getVisibility())) {
            throw new RuntimeException("Project is not available for applications.");
        }

        if (project.getRequests().contains(freelancerId)) {
            throw new IllegalStateException("Freelancer with ID " + freelancerId + " has already applied to project " + projectId + ".");
        }

        project.getRequests().add(freelancerId);
        return projectRepository.save(project); // Return the saved project
    }

    /**
     * Assigns a project to a freelancer. Sets the project status to "awaiting_freelancer_response".
     * @param projectId The ID of the project to assign.
     * @param freelancerId The ID of the freelancer to assign.
     * @return The updated Project object after assignment.
     * @throws RuntimeException if project or freelancer not found, or project cannot be assigned.
     */
    @Transactional
    public Project assignFreelancer(String projectId, String freelancerId) { // Changed return type to Project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found with ID: " + freelancerId));

        if (!"freelancer".equalsIgnoreCase(freelancer.getRole())) {
            throw new RuntimeException("Cannot assign project to a non-freelancer user.");
        }
        if (!"hiring".equalsIgnoreCase(project.getStatus())) {
            throw new RuntimeException("Project is not in 'hiring' status and cannot be assigned.");
        }
        if (project.getAssignedFreelancerId() != null && !project.getAssignedFreelancerId().isEmpty()) {
            throw new RuntimeException("Project already assigned to another freelancer.");
        }

        project.setAssignedFreelancerId(freelancerId);
        project.setStatus("awaiting_freelancer_response"); // New status: awaiting freelancer acceptance
        return projectRepository.save(project); // Return the saved project
    }

    /**
     * Undoes a project assignment, setting the project status back to "hiring".
     * Only applicable if the project is in "awaiting_freelancer_response" status.
     * @param projectId The ID of the project to undo assignment for.
     * @return The updated Project object after undoing assignment.
     * @throws RuntimeException if project not found or not in the correct status.
     */
    @Transactional
    public Project undoAssignment(String projectId) { // Changed return type to Project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        if (!"awaiting_freelancer_response".equalsIgnoreCase(project.getStatus())) {
            throw new RuntimeException("Project is not in 'awaiting_freelancer_response' status and cannot be undone.");
        }

        project.setAssignedFreelancerId(null); // Remove assigned freelancer
        project.setStatus("hiring"); // Set status back to hiring
        return projectRepository.save(project); // Return the saved project
    }

    /**
     * Allows a freelancer to accept an assigned project. Sets the project status to "assigned".
     * @param projectId The ID of the project to accept.
     * @param freelancerId The ID of the freelancer accepting.
     * @return The updated Project object after acceptance.
     * @throws RuntimeException if project or freelancer not found.
     * @throws IllegalStateException if project is not awaiting response or not assigned to this freelancer.
     */
    @Transactional
    public Project acceptAssignment(String projectId, String freelancerId) { // Changed return type to Project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        if (!"awaiting_freelancer_response".equalsIgnoreCase(project.getStatus())) {
            throw new IllegalStateException("Project is not awaiting freelancer response.");
        }
        if (!freelancerId.equals(project.getAssignedFreelancerId())) {
            throw new IllegalStateException("You are not the assigned freelancer for this project.");
        }

        project.setStatus("assigned");
        return projectRepository.save(project); // Return the saved project
    }

    /**
     * Allows a freelancer to decline an assigned project. Sets the project status back to "hiring" and unassigns the freelancer.
     * @param projectId The ID of the project to decline.
     * @param freelancerId The ID of the freelancer declining.
     * @return The updated Project object after declining.
     * @throws RuntimeException if project or freelancer not found.
     * @throws IllegalStateException if project is not awaiting response or not assigned to this freelancer.
     */
    @Transactional
    public Project declineAssignment(String projectId, String freelancerId) { // Changed return type to Project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        if (!"awaiting_freelancer_response".equalsIgnoreCase(project.getStatus())) {
            throw new IllegalStateException("Project is not awaiting freelancer response.");
        }
        if (!freelancerId.equals(project.getAssignedFreelancerId())) {
            throw new IllegalStateException("You are not the assigned freelancer for this project.");
        }

        project.setAssignedFreelancerId(null); // Unassign the freelancer
        project.setStatus("hiring"); // Revert status to hiring
        return projectRepository.save(project); // Return the saved project
    }

    /**
     * Retrieves all public projects (status "hiring" and visibility "public").
     * @return A list of public projects.
     */
    public List<Project> getPublicProjects() {
        return projectRepository.findByVisibilityAndStatus("public", "hiring");
    }

    /**
     * Retrieves all projects posted by a specific client.
     * @param clientId The ID of the client.
     * @return A list of projects posted by the client.
     */
    public List<Project> getClientProjects(String clientId) {
        return projectRepository.findByClientId(clientId);
    }

    /**
     * Retrieves all projects assigned to a specific freelancer.
     * @param freelancerId The ID of the freelancer.
     * @return A list of projects assigned to the freelancer.
     */
    public List<Project> getFreelancerAssignedProjects(String freelancerId) {
        return projectRepository.findByAssignedFreelancerId(freelancerId);
    }

    /**
     * Retrieves dashboard data for a client, categorizing projects into hiring, assigned, completed,
     * and projects awaiting freelancer acceptance.
     * @param clientId The ID of the client.
     * @return A map containing categorized project lists.
     * @throws RuntimeException if the client is not found or is not a client role.
     */
    public Map<String, Object> getClientDashboardData(String clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));

        if (!"client".equalsIgnoreCase(client.getRole())) {
            throw new RuntimeException("User with ID " + clientId + " is not a client.");
        }

        List<Project> clientProjects = projectRepository.findByClientId(clientId);

        List<Map<String, Object>> hiringProjectsWithApplicants = clientProjects.stream()
                .filter(p -> "hiring".equalsIgnoreCase(p.getStatus()))
                .map(p -> {
                    Map<String, Object> projectMap = new HashMap<>();
                    projectMap.put("project", p);
                    // Fetch full User objects for applicants
                    List<Map<String, Object>> applicantsDetails = p.getRequests().stream()
                            .map(freelancerId -> userRepository.findById(freelancerId).map(applicantUser -> {
                                Map<String, Object> applicantMap = new HashMap<>();
                                applicantMap.put("id", applicantUser.getId());
                                applicantMap.put("userId", applicantUser.getId()); // Ensure userId is available
                                applicantMap.put("name", applicantUser.getName());
                                applicantMap.put("email", applicantUser.getEmail());
                                // Fetch freelancer profile details if available
                                freelancerProfileRepository.findByUserId(applicantUser.getId()).ifPresent(freelancerProfile -> {
                                    applicantMap.put("experienceLevel", freelancerProfile.getExperienceLevel());
                                    applicantMap.put("companyName", freelancerProfile.getCompanyName());
                                    applicantMap.put("skills", freelancerProfile.getSkills());
                                    applicantMap.put("projectLinks", freelancerProfile.getProjectLinks());
                                    applicantMap.put("bio", freelancerProfile.getBio());
                                    applicantMap.put("githubLink", freelancerProfile.getGithubLink());
                                    applicantMap.put("linkedinLink", freelancerProfile.getLinkedInLink());
                                    applicantMap.put("hourlyRate", freelancerProfile.getHourlyRate());
                                });
                                return applicantMap;
                            }).orElse(null)) // Handle case where applicant user might not be found
                            .filter(Objects::nonNull) // Filter out nulls if any applicant user not found
                            .collect(Collectors.toList());
                    projectMap.put("applicants", applicantsDetails);
                    return projectMap;
                })
                .collect(Collectors.toList());

        List<Project> assignedProjects = clientProjects.stream()
                .filter(p -> "assigned".equalsIgnoreCase(p.getStatus()))
                .collect(Collectors.toList());

        List<Project> completedProjects = clientProjects.stream()
                .filter(p -> "completed".equalsIgnoreCase(p.getStatus()))
                .collect(Collectors.toList());

        List<Project> pendingFreelancerAcceptanceProjects = clientProjects.stream()
                .filter(p -> "awaiting_freelancer_response".equalsIgnoreCase(p.getStatus()))
                .collect(Collectors.toList());

        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("hiringProjects", hiringProjectsWithApplicants);
        dashboardData.put("assignedProjects", assignedProjects);
        dashboardData.put("completedProjects", completedProjects);
        dashboardData.put("pendingFreelancerAcceptanceProjects", pendingFreelancerAcceptanceProjects);

        return dashboardData;
    }

    /**
     * Retrieves dashboard data for a freelancer, categorizing projects into applied, assigned,
     * completed, and pending offers.
     * @param freelancerId The ID of the freelancer.
     * @return A map containing categorized project lists.
     * @throws RuntimeException if the freelancer is not found or is not a freelancer role.
     */
    public Map<String, Object> getFreelancerDashboardData(String freelancerId) {
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found with ID: " + freelancerId));

        if (!"freelancer".equalsIgnoreCase(freelancer.getRole())) {
            throw new RuntimeException("User with ID " + freelancerId + " is not a freelancer.");
        }

        // Projects applied to by this freelancer
        List<Project> appliedProjects = projectRepository.findByRequestsContains(freelancerId);

        // Projects assigned to this freelancer and are in 'assigned' status
        List<Project> assignedProjects = projectRepository.findByAssignedFreelancerIdAndStatus(freelancerId, "assigned");

        // Projects completed by this freelancer
        List<Project> completedProjects = projectRepository.findByAssignedFreelancerIdAndStatus(freelancerId, "completed");

        // Projects awaiting freelancer response (offers)
        List<Project> pendingAcceptanceProjects = projectRepository.findByAssignedFreelancerIdAndStatus(freelancerId, "awaiting_freelancer_response");

        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("appliedProjects", appliedProjects);
        dashboardData.put("assignedProjects", assignedProjects);
        dashboardData.put("completedProjects", completedProjects);
        dashboardData.put("pendingAcceptanceProjects", pendingAcceptanceProjects);

        return dashboardData;
    }

    /**
     * Searches for public projects by title (case-insensitive).
     * @param titleQuery The title keyword to search for.
     * @return A list of matching public projects in "hiring" status.
     */
    public List<Project> searchProjectsByTitle(String titleQuery) {
        return projectRepository.findByProjecttitleContainingIgnoreCaseAndVisibilityAndStatus(titleQuery, "public", "hiring");
    }

    /**
     * Searches for public projects by required skills (case-insensitive).
     * @param skillQuery The skill keyword to search for.
     * @return A list of matching public projects in "hiring" status.
     */
    public List<Project> searchProjectsBySkills(String skillQuery) {
        return projectRepository.findByRequiredSkillsContainingIgnoreCaseAndVisibilityAndStatus(skillQuery, "public", "hiring");
    }
}
