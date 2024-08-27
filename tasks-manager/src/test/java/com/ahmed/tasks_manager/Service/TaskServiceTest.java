package com.ahmed.tasks_manager.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ahmed.tasks_manager.Entities.Project;
import com.ahmed.tasks_manager.Entities.Task;
import com.ahmed.tasks_manager.Entities.User;
import com.ahmed.tasks_manager.Exceptions.ResourceNotFoundException;
import com.ahmed.tasks_manager.Repository.ProjectRepository;
import com.ahmed.tasks_manager.Repository.TaskRepository;
import com.ahmed.tasks_manager.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask_Success() {
        // Mock data
        User user = new User();
        user.setId(1);

        Project project = new Project();
        project.setId(1);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setUserID(1);
        task.setProjectId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Execute the service method
        Task createdTask = taskService.createTask(task);

        // Verify and assert
        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(userRepository, times(1)).findById(1);
        verify(projectRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testCreateTask_UserNotFound() {
        Task task = new Task();
        task.setUserID(1);
        task.setProjectId(1);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.createTask(task);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetAllTasks() {
        // Mock data
        Task task1 = new Task();
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setTitle("Task 2");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        // Execute the service method
        List<Task> tasks = taskService.getAllTasks();

        // Verify and assert
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById_Success() {
        Task task = new Task();
        task.setId(1);
        task.setTitle("Test Task");

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1);

        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getTitle());
        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    public void testGetTaskById_TaskNotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTaskById(1);
        });

        String expectedMessage = "Task not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateTask_Success() {
        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTitle("Old Task");

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");

        when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testDeleteTask_Success() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(1);

        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    public void testSearchTasksByTitle() {
        Task task = new Task();
        task.setTitle("Task Title");

        when(taskRepository.findByTitleContaining("Title")).thenReturn(List.of(task));

        List<Task> tasks = taskService.searchTasksByTitle("Title");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findByTitleContaining("Title");
    }

}
