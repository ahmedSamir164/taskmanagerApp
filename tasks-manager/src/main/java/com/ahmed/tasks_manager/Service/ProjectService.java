package com.ahmed.tasks_manager.Service;

import com.ahmed.tasks_manager.Entities.Project;
import com.ahmed.tasks_manager.Entities.Task;
import com.ahmed.tasks_manager.Exceptions.ResourceNotFoundException;
import com.ahmed.tasks_manager.Repository.ProjectRepository;
import com.ahmed.tasks_manager.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    public Project createProject(Project project) {
        project.setCreated_date(LocalDate.now());
        projectRepository.save(project);
        return project;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(int projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    public Project updateProject(int projectId, Project project) {
        Project oldProject = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project not found!"));
        oldProject.setName(project.getName());
        oldProject.setDescription(project.getDescription());
        projectRepository.deleteById(projectId);
        projectRepository.save(oldProject);
        return oldProject;
    }

    public void deleteProject(int projectId) {
        Project deletedProject = projectRepository.findById(projectId).
                orElseThrow(()-> new ResourceNotFoundException("Project not found!"));
        List<Task> projectTasks = deletedProject.getTasks();
        for(Task task : projectTasks){
            taskRepository.delete(task);
        }
        projectRepository.delete(deletedProject);
    }
}
