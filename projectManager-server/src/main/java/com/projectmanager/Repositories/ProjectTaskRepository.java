package com.projectmanager.Repositories;

import com.projectmanager.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    public List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
}
