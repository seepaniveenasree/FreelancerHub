package com.project.freelancehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty; // Import this annotation

import java.util.List;

@Document(collection = "freelancer_profile")
public class FreelancerProfile {
    @Id
    private String id;

    private String userId;
    private String experienceLevel;
    private String companyName;
    private List<String> skills;
    private List<String> projectLinks;
    private String bio;
    
    private String githubLink;
    
    @JsonProperty("linkedinLink") // Explicitly map the JSON field "linkedinLink"
    private String linkedInLink; 
    
    private double hourlyRate;

    // Getters and setters for all fields.
    // Ensure the naming conventions are consistent (e.g., getLinkedInLink, setLinkedInLink)
    // and that they are public.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getProjectLinks() {
        return projectLinks;
    }

    public void setProjectLinks(List<String> projectLinks) {
        this.projectLinks = projectLinks;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getLinkedInLink() { // Crucial: Ensure this getter exists and is public
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) { // Crucial: Ensure this setter exists and is public
        this.linkedInLink = linkedInLink;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
