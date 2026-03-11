package com.example.Taskmanagement.Repo;

import com.example.Taskmanagement.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RProle extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}