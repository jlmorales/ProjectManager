package com.projectmanager.Services;

import com.projectmanager.Exceptions.ProjectIdException;
import com.projectmanager.Repositories.ProjectRepository;
import com.projectmanager.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }
}
