package com.project.freelancehub.controller;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.freelancehub.service.ClientDashboardService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/dashboard/client")
public class ClientDashboardController {

    @Autowired
    private ClientDashboardService dashboardService;

    @GetMapping("/{clientId}")
    public Map<String, Object> getClientDashboard(@PathVariable String clientId) {
        return dashboardService.getDashboard(clientId);
    }
}