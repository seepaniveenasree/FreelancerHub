// src/main/java/com/project/freelancehub/dto/UserRegistrationRequest.java
package com.project.freelancehub.dto;

import java.util.List;

public class UserRegistrationRequest {
    private String name;
    private String email;
    private String password;
    private String role;

    // Only for freelancers
    private String experienceLevel;
    private String companyName;
    private List<String> skills;
    private List<String> projectLinks;
    private String bio;
    private String githubLink;
    private String linkedInLink;
    private double hourlyRate; // <--- ADD THIS LINE

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public List<String> getProjectLinks() { return projectLinks; }
    public void setProjectLinks(List<String> projectLinks) { this.projectLinks = projectLinks; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getGithubLink() { return githubLink; }
    public void setGithubLink(String githubLink) { this.githubLink = githubLink; }
    public String getLinkedInLink() { return linkedInLink; }
    public void setLinkedInLink(String linkedInLink) { this.linkedInLink = linkedInLink; }

    // New getter and setter for hourlyRate
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
}