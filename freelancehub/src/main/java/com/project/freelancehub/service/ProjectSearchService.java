package com.project.freelancehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.freelancehub.model.Project;
import com.project.freelancehub.repository.ProjectRepository;
import java.util.*;
@Service
public class ProjectSearchService {

    @Autowired
    private ProjectRepository projectRepo;

    public List<Project> searchByTitle(String keyword) {
        return projectRepo.findByProjecttitleContainingIgnoreCase(keyword);
    }

}