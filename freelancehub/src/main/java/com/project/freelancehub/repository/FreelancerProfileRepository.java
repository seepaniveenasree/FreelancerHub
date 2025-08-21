package com.project.freelancehub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.project.freelancehub.model.FreelancerProfile;

public interface FreelancerProfileRepository extends MongoRepository<FreelancerProfile, String> {

    Optional<FreelancerProfile> findByUserId(String userId);

    // Methods for searching freelancers by experience level and skills
    List<FreelancerProfile> findByExperienceLevelContainingIgnoreCase(String level);
    List<FreelancerProfile> findBySkillsContainingIgnoreCase(String skill);
}
