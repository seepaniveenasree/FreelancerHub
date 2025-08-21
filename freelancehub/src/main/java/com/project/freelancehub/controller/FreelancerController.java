
package com.project.freelancehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.freelancehub.dto.FreelancerWithUserDetailsDTO;
import com.project.freelancehub.service.FreelancerService;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerService freelancerService;

    @GetMapping
    public List<FreelancerWithUserDetailsDTO> getAllFreelancers() {
        // Corrected line: Call on the instance 'freelancerService', not the class name
        return freelancerService.getAllFreelancers(); // <--- FIX HERE
    }
}