package com.ahmed.tasks_manager.Service;

import com.ahmed.tasks_manager.Entities.*;
import com.ahmed.tasks_manager.Exceptions.ResourceNotFoundException;
import com.ahmed.tasks_manager.Repository.ProjectRepository;
import com.ahmed.tasks_manager.Repository.TaskRepository;
import com.ahmed.tasks_manager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Task createTask(Task task) {

        User assignedUser = userRepository.findById(task.getUserID())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        assignedUser.setTasks(task);

        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        project.setTasks(task);


        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public Task updateTask(int taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());
        task.setUserID(updatedTask.getUserID());
        task.setProjectId(updatedTask.getProjectId());

        return taskRepository.save(task);
    }

    public void deleteTask(int taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContaining(title);
    }

    public List<Task> searchTasksByDescription(String description) {
        return taskRepository.findByDescriptionContaining(description);
    }

    public List<Task> searchTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> searchTasksByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }

}

