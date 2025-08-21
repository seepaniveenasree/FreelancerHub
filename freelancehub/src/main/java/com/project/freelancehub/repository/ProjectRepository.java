package com.project.freelancehub.repository;

import com.project.freelancehub.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findByVisibility(String visibility);

    List<Project> findByClientId(String clientId);

    List<Project> findByAssignedFreelancerId(String assignedFreelancerId);

    List<Project> findByRequestsContains(String freelancerId);

    List<Project> findByProjecttitleContainingIgnoreCase(String projectTitle);
    List<Project> findByRequiredSkillsContainingIgnoreCase(String skill);

    List<Project> findByStatus(String status);

    // ⭐ NEW METHODS ADDED FOR ProjectService ⭐
    List<Project> findByVisibilityAndStatus(String visibility, String status);
    List<Project> findByAssignedFreelancerIdAndStatus(String assignedFreelancerId, String status);
    List<Project> findByProjecttitleContainingIgnoreCaseAndVisibilityAndStatus(String projectTitle, String visibility, String status);
    List<Project> findByRequiredSkillsContainingIgnoreCaseAndVisibilityAndStatus(String skill, String visibility, String status);
}
