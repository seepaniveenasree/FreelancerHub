package com.project.freelancehub.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;
import com.project.freelancehub.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    
    List<User> findByRoleAndNameContainingIgnoreCase(String role, String name);
    List<User> findByNameContainingIgnoreCase(String name);
    
 // src/main/java/com/project/freelancehub/service/UserService.java
 // (Ensure this method is present and correct in your UserService)

}
