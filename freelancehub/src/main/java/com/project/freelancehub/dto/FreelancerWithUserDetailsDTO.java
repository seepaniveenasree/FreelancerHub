// src/main/java/com/project/freelancehub/dto/FreelancerWithUserDetailsDTO.java
package com.project.freelancehub.dto;

import com.project.freelancehub.model.FreelancerProfile;
import java.util.List;

public class FreelancerWithUserDetailsDTO {
    private String id; // This will be the FreelancerProfile ID
    private String userId; // The associated User's ID
    private String email;
    private String name; // <--- This field MUST be here

    private String experienceLevel;
    private String companyName;
    private List<String> skills;
    private List<String> projectLinks;
    private String bio;
    private String githubLink;
    private String linkedInLink;
    private double hourlyRate;

    // Constructor: Ensures 'name' and 'email' are received and assigned
    public FreelancerWithUserDetailsDTO(FreelancerProfile profile, String name, String email) {
        this.id = profile.getId();
        this.userId = profile.getUserId();
        this.email = email;
        this.name = name; // <--- Crucial: Assign the 'name' here
        this.experienceLevel = profile.getExperienceLevel();
        this.companyName = profile.getCompanyName();
        this.skills = profile.getSkills();
        this.projectLinks = profile.getProjectLinks();
        this.bio = profile.getBio();
        this.githubLink = profile.getGithubLink();
        this.linkedInLink = profile.getLinkedInLink();
        this.hourlyRate = profile.getHourlyRate();
    }

    // Getters for all fields
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getName() { return name; } // <--- This getter MUST be here
    public String getExperienceLevel() { return experienceLevel; }
    public String getCompanyName() { return companyName; }
    public List<String> getSkills() { return skills; }
    public List<String> getProjectLinks() { return projectLinks; }
    public String getBio() { return bio; }
    public String getGithubLink() { return githubLink; }
    public String getLinkedInLink() { return linkedInLink; }
    public double getHourlyRate() { return hourlyRate; }
}