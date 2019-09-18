package com.projectmanager.Services;

import com.projectmanager.Repositories.BacklogRepository;
import com.projectmanager.Repositories.ProjectTaskRepository;
import com.projectmanager.domain.Backlog;
import com.projectmanager.domain.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        //exception: project not found
        //task added to a project wher project is not Null and Bl exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        //set bl to task
        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;

        projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        //we want project sequence to be like: IDPRO-1 IDPRO-2
        //Update the BL sequence
        //Initial priority if priority null
//        if(projectTask.getPriority() == 0 || projectTask.getPriority() == null){
//            projectTask.setPriority(3);
//        }
        if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
        //Init status when status null
    }
}
