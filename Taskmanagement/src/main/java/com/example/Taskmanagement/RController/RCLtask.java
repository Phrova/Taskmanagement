package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.TaskDTO;
import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.Model.Task;
import com.example.Taskmanagement.Service.SVtask_w4;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            TaskDTO created = sv.createTask(taskDTO);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<TaskDTO> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            TaskDTO updated = sv.assignTaskToUser(taskId, userId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        try {
            TaskDTO updated = sv.updateTaskStatus(taskId, status);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}