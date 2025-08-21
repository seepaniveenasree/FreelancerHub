    package com.project.freelancehub.controller;

    import com.project.freelancehub.dto.ApplicationEmailRequest;
    import com.project.freelancehub.service.EmailService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @CrossOrigin(origins = "http://localhost:3000")
    @RestController
    @RequestMapping("/api/email")
    public class NotificationController {

        @Autowired
        private EmailService emailService;

        @PostMapping("/send-hire-notification")
        public ResponseEntity<String> sendHireNotification(@RequestBody ApplicationEmailRequest request) {
            try {
                emailService.sendApplicationNotification(request);
                return ResponseEntity.ok("Hire notification email sent successfully.");
            } catch (Exception e) {
                System.err.println("Error sending hire notification: " + e.getMessage());
                e.printStackTrace(); // Log the full stack trace on the server
                return ResponseEntity.status(500).body("Failed to send hire notification email: " + e.getMessage());
            }
        }
    }
    