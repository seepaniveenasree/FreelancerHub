    package com.project.freelancehub.dto;

    // This DTO will carry the necessary information from the frontend
    // to the backend for sending an email notification to the client
    public class ApplicationEmailRequest {
        private String projectId;
        private String projectTitle;
        private String clientId; // ID of the client who owns the project
        private String freelancerId;
        private String freelancerName;
        private String freelancerEmail;

        // Default constructor is needed for JSON deserialization
        public ApplicationEmailRequest() {
        }

        // Constructor with all fields (optional, but good for testing/convenience)
        public ApplicationEmailRequest(String projectId, String projectTitle, String clientId, String freelancerId, String freelancerName, String freelancerEmail) {
            this.projectId = projectId;
            this.projectTitle = projectTitle;
            this.clientId = clientId;
            this.freelancerId = freelancerId;
            this.freelancerName = freelancerName;
            this.freelancerEmail = freelancerEmail;
        }

        // Getters and Setters for all fields
        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getProjectTitle() {
            return projectTitle;
        }

        public void setProjectTitle(String projectTitle) {
            this.projectTitle = projectTitle;
        }

        public String getClientId() { // This getter is crucial and must be present
            return clientId;
        }

        public void setClientId(String clientId) { // This setter is crucial and must be present
            this.clientId = clientId;
        }

        public String getFreelancerId() {
            return freelancerId;
        }

        public void setFreelancerId(String freelancerId) {
            this.freelancerId = freelancerId;
        }

        public String getFreelancerName() {
            return freelancerName;
        }

        public void setFreelancerName(String freelancerName) {
            this.freelancerName = freelancerName;
        }

        public String getFreelancerEmail() {
            return freelancerEmail;
        }

        public void setFreelancerEmail(String freelancerEmail) {
            this.freelancerEmail = freelancerEmail;
        }
    }
    