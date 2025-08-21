package com.project.freelancehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.freelancehub.dto.FreelancerWithUserDetailsDTO; // Import the DTO
import com.project.freelancehub.service.FreelancerSearchService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/search/freelancers")
public class FreelancerSearchController {

    @Autowired
    private FreelancerSearchService searchService;

    @GetMapping("/skills/{skill}")
    public List<FreelancerWithUserDetailsDTO> searchBySkill(@PathVariable String skill) {
        return searchService.searchBySkill(skill);
    }

    @GetMapping("/experience/{level}")
    public List<FreelancerWithUserDetailsDTO> searchByExperience(@PathVariable String level) {
        return searchService.searchByExperience(level);
    }

    // New endpoint for searching by name
    @GetMapping("/name/{name}")
    public List<FreelancerWithUserDetailsDTO> searchByName(@PathVariable String name) {
        return searchService.searchByName(name);
    }
}
