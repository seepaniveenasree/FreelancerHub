package com.project.freelancehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List; // Keep this import for List

import com.project.freelancehub.model.Project;
import com.project.freelancehub.service.ProjectSearchService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/search/projects")
public class ProjectSearchController {

    @Autowired
    private ProjectSearchService searchService;

    @GetMapping("/title/{keyword}")
    public List<Project> searchByTitle(@PathVariable String keyword) {
        return searchService.searchByTitle(keyword);
    }

    // Removed the endpoint for filtering projects by status as requested.
    // @GetMapping("/status/{status}")
    // public List<Project> filterByStatus(@PathVariable String status) {
    //     return searchService.filterByStatus(status);
    // }
}
