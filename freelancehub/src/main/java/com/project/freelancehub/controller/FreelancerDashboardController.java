package com.project.freelancehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.project.freelancehub.service.FreelancerDashboardService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/dashboard/freelancer")
public class FreelancerDashboardController {

    @Autowired
    private FreelancerDashboardService dashboardService;

    @GetMapping("/{freelancerId}")
    public Map<String, Object> getFreelancerDashboard(@PathVariable String freelancerId) {
        return dashboardService.getDashboard(freelancerId);
    }
}