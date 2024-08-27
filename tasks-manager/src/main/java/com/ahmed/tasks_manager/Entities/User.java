package com.ahmed.tasks_manager.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank (message = "Name can't be empty")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;
    @NotBlank (message = "username can't be empty")
    @Size(max = 100, message = "username should not exceed 100 characters")
    private String username;
    @NotBlank (message="Email can't be empty")
    @Email (message = "you must enter a valid email")
    @Size(max = 200, message = "Email should not exceed 200 characters")
    private String email;
    @NotBlank (message = "password can't be empty")
    @Size(max = 100, message = "password should not exceed 100 characters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany
    private List<Task> tasks = new ArrayList<>();

    public void setTasks(Task task) {
        this.tasks.add(task);
    }
}



