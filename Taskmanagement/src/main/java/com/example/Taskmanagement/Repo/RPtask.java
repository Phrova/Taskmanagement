package com.example.Taskmanagement.Repo;

import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RPtask extends JpaRepository<Task, Long> {
}
