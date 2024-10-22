package com.projectmanager.Services;

import com.projectmanager.Exceptions.ProjectIdException;
import com.projectmanager.Repositories.BacklogRepository;
import com.projectmanager.Repositories.ProjectRepository;
import com.projectmanager.domain.Backlog;
import com.projectmanager.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }

    public Project findProjectByProjectId(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null)
            throw new ProjectIdException("Project with Id '" + projectId.toUpperCase() + "' doesNotExist");
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return this.projectRepository.findAll();
    }

    public void deleteProjectById(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null)
            throw new ProjectIdException("Cannot delete Project with Id '" + projectId.toUpperCase() + "' doesNotExist");
        projectRepository.delete(project);
    }
}
