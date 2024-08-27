package com.ahmed.tasks_manager.Repository;

import java.util.Optional;

import com.ahmed.tasks_manager.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
