package com.project.freelancehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.project.freelancehub.model.Feedback;
import com.project.freelancehub.model.Project;
import com.project.freelancehub.repository.FeedbackRepository;
import com.project.freelancehub.repository.ProjectRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepo;

    @Autowired
    private ProjectRepository projectRepo;

    public Feedback giveFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }

    public List<Feedback> getFeedbackForUser(String userId) {
        return feedbackRepo.findByToUserId(userId);
    }

    public Project markProjectCompleted(String projectId) {
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project != null) {
            project.setStatus("completed");
            return projectRepo.save(project);
        }
        return null;
    }
}