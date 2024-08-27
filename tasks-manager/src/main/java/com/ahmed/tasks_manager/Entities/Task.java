package com.ahmed.tasks_manager.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title should not exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Priority is mandatory")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is mandatory")
    private Status status;

    @PastOrPresent(message = "Created date cannot be in the future")
    private LocalDateTime createdDate;

    @Min(value = 1, message = "Project ID must be a positive number")
    private int projectId;

    @Min(value = 1, message = "User ID must be a positive number")
    private int userID;

}
