package com.ahmed.tasks_manager.Service;
import com.ahmed.tasks_manager.Entities.Project;
import com.ahmed.tasks_manager.Entities.Task;
import com.ahmed.tasks_manager.Exceptions.ResourceNotFoundException;
import com.ahmed.tasks_manager.Repository.ProjectRepository;
import com.ahmed.tasks_manager.Repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();
        project.setName("New Project");
        project.setDescription("Project Description");

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project createdProject = projectService.createProject(project);

        assertNotNull(createdProject);
        assertEquals("New Project", createdProject.getName());
        assertEquals("Project Description", createdProject.getDescription());
        assertNotNull(createdProject.getCreated_date());

        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testGetAllProjects() {
        Project project1 = new Project();
        project1.setName("Project 1");

        Project project2 = new Project();
        project2.setName("Project 2");

        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertEquals("Project 1", projects.get(0).getName());
        assertEquals("Project 2", projects.get(1).getName());

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testGetProjectById_ProjectFound() {
        Project project = new Project();
        project.setName("Existing Project");

        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        Project foundProject = projectService.getProjectById(1);

        assertNotNull(foundProject);
        assertEquals("Existing Project", foundProject.getName());

        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    public void testGetProjectById_ProjectNotFound() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projectService.getProjectById(1));

        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateProject_ProjectFound() {
        Project oldProject = new Project();
        oldProject.setName("Old Project");
        oldProject.setDescription("Old Description");

        Project updatedProject = new Project();
        updatedProject.setName("Updated Project");
        updatedProject.setDescription("Updated Description");

        when(projectRepository.findById(1)).thenReturn(Optional.of(oldProject));
        when(projectRepository.save(any(Project.class))).thenReturn(oldProject);

        Project result = projectService.updateProject(1, updatedProject);

        assertNotNull(result);
        assertEquals("Updated Project", result.getName());
        assertEquals("Updated Description", result.getDescription());

        verify(projectRepository, times(1)).findById(1);
        verify(projectRepository, times(1)).save(oldProject);
    }

    @Test
    public void testUpdateProject_ProjectNotFound() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        Project updatedProject = new Project();
        updatedProject.setName("Updated Project");
        updatedProject.setDescription("Updated Description");

        assertThrows(ResourceNotFoundException.class, () -> projectService.updateProject(1, updatedProject));

        verify(projectRepository, times(1)).findById(1);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void testDeleteProject() {

        int projectId = 1;

        Project project = new Project();
        project.setId(projectId);
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1);
        Task task2 = new Task();
        task2.setId(2);
        tasks.add(task1);
        tasks.add(task2);
        project.setTasks(tasks);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.deleteProject(projectId);

        verify(taskRepository, times(1)).deleteById(task1.getId());
        verify(taskRepository, times(1)).deleteById(task2.getId());
        verify(projectRepository, times(1)).deleteById(projectId);
    }
}

