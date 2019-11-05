package com.projectmanager.Services;

import com.projectmanager.Exceptions.ProjectNotFoundException;
import com.projectmanager.Repositories.BacklogRepository;
import com.projectmanager.Repositories.ProjectRepository;
import com.projectmanager.Repositories.ProjectTaskRepository;
import com.projectmanager.domain.Backlog;
import com.projectmanager.domain.Project;
import com.projectmanager.domain.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        try {
            //exception: project not found
            //task added to a project wher project is not Null and Bl exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            //set bl to task
            Integer BacklogSequence = backlog.getPTSequence();
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);

            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            //we want project sequence to be like: IDPRO-1 IDPRO-2
            //Update the BL sequence
            //Initial priority if priority null
            if(projectTask.getPriority() == null){
                projectTask.setPriority(3);
            }
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
            //Init status when status null
        }catch (Exception e){
            throw new ProjectNotFoundException("Project Not Found");

        }

    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        Project project = projectRepository.findByProjectIdentifier(backlog_id);
        if (project == null)
            throw new ProjectNotFoundException("Project with Id: '" + backlog_id + "' doesn't exit");
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){
        //check we are searching a real backlog
        //check that task exists
        //check that backlog/project_id correspond to project
        //
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null)
            throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist");
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null)
            throw new ProjectNotFoundException("Project with ID: '" + pt_id + "' does not exist");
        if(!projectTask.getProjectIdentifier().equals(backlog_id))
            throw new ProjectNotFoundException("Project Task: '" + pt_id + "' is not in project: '" + backlog_id + "'");
        //else
        return projectTask;
    }
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String projectTask_id){
        //follow similar process to above check if backlog, projectTask exist
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, projectTask_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deleteProjectTaskByProjectSequence(String backlog_id, String projectTask_id){
        //check again if project task exists using find by id
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, projectTask_id);
        projectTaskRepository.delete(projectTask);
    }
}
