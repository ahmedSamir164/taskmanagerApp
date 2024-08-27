package com.ahmed.tasks_manager.Repository;

import com.ahmed.tasks_manager.Entities.Priority;
import com.ahmed.tasks_manager.Entities.Status;
import com.ahmed.tasks_manager.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <Task, Integer> {
    List<Task> findByTitleContaining(String title);
    List<Task> findByDescriptionContaining(String description);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatus(Status status);
}
