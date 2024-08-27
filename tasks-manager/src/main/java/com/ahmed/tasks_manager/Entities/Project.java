package com.ahmed.tasks_manager.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message ="Name must not be empty")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;
    @NotBlank(message ="This field must not be empty")
    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;
    @PastOrPresent(message = "Created date cannot be in the future")
    private LocalDate created_date;
    @OneToMany
    private List<Task> tasks= new ArrayList<>();

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setTasks(Task task) {
        this.tasks.add(task);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}
