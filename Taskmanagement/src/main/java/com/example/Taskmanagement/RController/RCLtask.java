package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.TaskDTO;
import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.Model.Task;
import com.example.Taskmanagement.Service.SVtask_w4;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class RCLtask {

    private final SVtask_w4 sv;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO created = sv.createTask(taskDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Task> getAll() {
        return sv.getAll();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return sv.getById(id);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return sv.update(id, task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sv.delete(id);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getByUser(@PathVariable Long userId) {
        return sv.getByUserId(userId);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getByProject(@PathVariable Long projectId) {
        return sv.getByProjectId(projectId);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public TaskDTO assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return sv.assignTaskToUser(taskId, userId);
    }

    @PatchMapping("/{taskId}/status")
    public TaskDTO updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        return sv.updateTaskStatus(taskId, status);
    }
}