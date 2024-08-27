package com.ahmed.tasks_manager.Repository;

import com.ahmed.tasks_manager.Entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository <Project , Integer> {
}
