package com.project.freelancehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.project.freelancehub.model.Feedback;
import com.project.freelancehub.model.Project;
import com.project.freelancehub.service.FeedbackService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public Feedback submitFeedback(@RequestBody Feedback feedback) {
        return feedbackService.giveFeedback(feedback);
    }

    @GetMapping("/user/{userId}")
    public List<Feedback> getFeedbackForUser(@PathVariable String userId) {
        return feedbackService.getFeedbackForUser(userId);
    }

    @PutMapping("/complete/{projectId}")
    public Project completeProject(@PathVariable String projectId) {
        return feedbackService.markProjectCompleted(projectId);
    }
}