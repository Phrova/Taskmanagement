package com.example.Taskmanagement.Repo;

import com.example.Taskmanagement.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RPproject extends JpaRepository<Project, Long> {
}
