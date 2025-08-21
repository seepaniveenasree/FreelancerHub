package com.project.freelancehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed; // Import this
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String name;

    @Indexed(unique = true) // ⭐ ADD THIS ANNOTATION ⭐
    private String email;
    private String password;
    private String role; // e.g., "client", "freelancer"
    private String freelancerProfileId; // To link to freelancer profile if user is a freelancer

    // Constructors
    public User() {
    }

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    // ⭐ TYPO FIX: Changed "public void void setEmail" to "public void setEmail" ⭐
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFreelancerProfileId() {
        return freelancerProfileId;
    }

    public void setFreelancerProfileId(String freelancerProfileId) {
        this.freelancerProfileId = freelancerProfileId;
    }
}
