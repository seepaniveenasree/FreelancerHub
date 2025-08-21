package com.project.freelancehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate; // Import LocalDate for deadline
import java.util.ArrayList;
import java.util.List;

@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String projecttitle; // Renamed from title to projecttitle to match frontend
    private String description;
    private String budget;
    private List<String> requiredSkills;
    private String clientId;
    private String assignedFreelancerId; // Null if not assigned
    private String status; // e.g., "hiring", "assigned", "completed", "pending_acceptance"
    private String visibility; // e.g., "public", "private"
    private LocalDate deadline; // Added deadline field
    private List<String> requests; // Changed from List<Requestentry>

    public Project() {
        this.requests = new ArrayList<>(); // Initialize to prevent NullPointerException
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjecttitle() {
        return projecttitle;
    }

    public void setProjecttitle(String projecttitle) {
        this.projecttitle = projecttitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAssignedFreelancerId() {
        return assignedFreelancerId;
    }

    public void setAssignedFreelancerId(String assignedFreelancerId) {
        this.assignedFreelancerId = assignedFreelancerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public List<String> getRequests() {
        return requests;
    }

    public void setRequests(List<String> requests) {
        this.requests = requests;
    }
}
