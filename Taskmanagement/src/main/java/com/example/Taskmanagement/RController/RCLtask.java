package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.ResApi;
import com.example.Taskmanagement.DTO.TaskDTO;
import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.Model.Task;
import com.example.Taskmanagement.Service.SVtask_w4;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResApi<TaskDTO>> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO created = sv.createTask(taskDTO);
        ResApi<TaskDTO> response = new ResApi<>(201, "Task created successfully", created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResApi<List<Task>>> getAll() {
        List<Task> tasks = sv.getAll();
        ResApi<List<Task>> response = new ResApi<>(200, "Success", tasks);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResApi<Task>> getById(@PathVariable Long id) {
        Task task = sv.getById(id);
        ResApi<Task> response = new ResApi<>(200, "Success", task);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResApi<Task>> update(@PathVariable Long id, @RequestBody Task task) {
        Task updated = sv.update(id, task);
        ResApi<Task> response = new ResApi<>(200, "Task updated successfully", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResApi<Void>> delete(@PathVariable Long id) {
        sv.delete(id);
        ResApi<Void> response = new ResApi<>(200, "Task deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResApi<List<Task>>> getByUser(@PathVariable Long userId) {
        List<Task> tasks = sv.getByUserId(userId);
        ResApi<List<Task>> response = new ResApi<>(200, "Success", tasks);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResApi<List<Task>>> getByProject(@PathVariable Long projectId) {
        List<Task> tasks = sv.getByProjectId(projectId);
        ResApi<List<Task>> response = new ResApi<>(200, "Success", tasks);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<ResApi<TaskDTO>> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        TaskDTO updated = sv.assignTaskToUser(taskId, userId);
        ResApi<TaskDTO> response = new ResApi<>(200, "User assigned successfully", updated);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ResApi<TaskDTO>> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        TaskDTO updated = sv.updateTaskStatus(taskId, status);
        ResApi<TaskDTO> response = new ResApi<>(200, "Status updated successfully", updated);
        return ResponseEntity.ok(response);
    }
}