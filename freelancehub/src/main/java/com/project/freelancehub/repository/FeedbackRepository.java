package com.project.freelancehub.repository;
import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.freelancehub.model.Feedback;
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByToUserId(String toUserId);
}