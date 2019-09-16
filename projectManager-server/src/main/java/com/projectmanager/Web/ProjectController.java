package com.projectmanager.Web;

import com.projectmanager.Services.ProjectService;
import com.projectmanager.Services.ValidationErrorService;
import com.projectmanager.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult bindingResult){

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(bindingResult);
        if(errorMap != null)
            return errorMap;
        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectByProjectId(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);

    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return  projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId){
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<String>("Project with id '" + projectId + "' was deleted", HttpStatus.OK);
    }
}
