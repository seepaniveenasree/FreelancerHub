    package com.project.freelancehub.service;

    import com.project.freelancehub.dto.ApplicationEmailRequest;
    import com.project.freelancehub.model.User;
    import com.project.freelancehub.model.FreelancerProfile;
    import com.project.freelancehub.model.Project;
    import com.project.freelancehub.repository.UserRepository;
    import com.project.freelancehub.repository.FreelancerProfileRepository;
    import com.project.freelancehub.repository.ProjectRepository;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;
    import org.bson.types.ObjectId; // Make sure this is imported if you're using ObjectId

    @Service
    public class EmailService {

        @Autowired
        private JavaMailSender mailSender;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private FreelancerProfileRepository freelancerProfileRepository;

        @Autowired
        private ProjectRepository projectRepository;

        public void sendApplicationNotification(ApplicationEmailRequest request) {
            String clientIdString = request.getClientId();
            System.out.println("EmailService: Received request for Project ID: " + request.getProjectId() + ", Client ID (String): " + clientIdString);

            // Fetch the client (project owner)
            Optional<User> clientOptional = userRepository.findById(clientIdString);
            if (clientOptional.isEmpty()) {
                System.err.println("EmailService: Client not found with ID: " + clientIdString);
                return; // Exit if client not found
            }
            User client = clientOptional.get();
            String clientEmail = client.getEmail();
            String clientName = client.getName();

            // Fetch the project to get full details
            Optional<Project> projectOptional = projectRepository.findById(request.getProjectId());
            Project project = projectOptional.orElse(null);

            // Fetch the freelancer's full profile for more details in the email
            Optional<FreelancerProfile> freelancerProfileOptional = freelancerProfileRepository.findByUserId(request.getFreelancerId());
            FreelancerProfile freelancerProfile = freelancerProfileOptional.orElse(null);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("freelancehubteam41@gmail.com"); // Your sending email address
            message.setTo(request.getFreelancerEmail()); // Send to the freelancer
            message.setSubject("Project Offer: " + request.getProjectTitle() + " from " + clientName);

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("Dear ").append(request.getFreelancerName()).append(",\n\n");
            emailBody.append("Great news! ").append(clientName).append(" (").append(clientEmail).append(") is interested in hiring you for their project.\n\n");

            emailBody.append("Project Details:\n");
            emailBody.append("Title: ").append(request.getProjectTitle()).append("\n");
            if (project != null) {
                emailBody.append("Description: ").append(project.getDescription()).append("\n");
                emailBody.append("Budget: ").append(project.getBudget()).append("\n");
                emailBody.append("Required Skills: ").append(String.join(", ", project.getRequiredSkills())).append("\n");
                if (project.getDeadline() != null) {
                    emailBody.append("Deadline: ").append(project.getDeadline().toString()).append("\n");
                }
            } else {
                emailBody.append("Description: N/A\nBudget: N/A\nRequired Skills: N/A\n");
            }

            emailBody.append("\nTo accept or decline this offer, please visit your Freelancer Dashboard.\n\n");
            emailBody.append("Best regards,\nThe Freelance Hub Team\n");
            emailBody.append("Client ID: ").append(clientIdString).append("\n"); // Include Client ID for debugging/reference
            emailBody.append("Freelancer ID: ").append(request.getFreelancerId()).append("\n"); // Include Freelancer ID

            message.setText(emailBody.toString());

            try {
                mailSender.send(message);
                System.out.println("EmailService: Project offer email sent successfully to freelancer: " + request.getFreelancerEmail() + " for project: " + request.getProjectTitle());
            } catch (Exception e) {
                System.err.println("EmailService: Failed to send email to " + request.getFreelancerEmail() + ": " + e.getMessage());
                e.printStackTrace(); // Print full stack trace for debugging
            }
        }
    }
    