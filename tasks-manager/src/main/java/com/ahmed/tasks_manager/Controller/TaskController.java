package com.ahmed.tasks_manager.Controller;

import com.ahmed.tasks_manager.Entities.Priority;
import com.ahmed.tasks_manager.Entities.Status;
import com.ahmed.tasks_manager.Entities.Task;
import com.ahmed.tasks_manager.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable int taskId) {
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody Task taskDto) {
        Task updatedTask = taskService.updateTask(taskId, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    // Search for tasks
    @GetMapping("/search/byTitle")
    public ResponseEntity<List<Task>> searchTasksByTitle(
            @RequestParam(required = false) String title) {

        List<Task> tasks = taskService.searchTasksByTitle(title);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/search/byDescription")
    public ResponseEntity<List<Task>> searchTasksByDescription(
            @RequestParam(required = false) String description) {

        List<Task> tasks = taskService.searchTasksByDescription(description);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search/byPriority")
    public ResponseEntity<List<Task>> searchTasksByPriority(
            @RequestParam(required = false) Priority priority) {

        List<Task> tasks = taskService.searchTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search/byStatus")
    public ResponseEntity<List<Task>> searchTasksByStatus(
            @RequestParam(required = false) Status status) {

        List<Task> tasks = taskService.searchTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }
}

