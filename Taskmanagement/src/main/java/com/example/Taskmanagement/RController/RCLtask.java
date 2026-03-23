package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.ResApi;
import com.example.Taskmanagement.DTO.TaskDTO;
import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.Model.Task;
import com.example.Taskmanagement.Service.SVtask_w4;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class RCLtask {

    private final SVtask_w4 sv;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create a new task", description = "Creates a task with validation, optional user/project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ResApi<TaskDTO>> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO created = sv.createTask(taskDTO);
        return new ResponseEntity<>(new ResApi<>(201, "Task created successfully", created), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Get all tasks", description = "Returns list of all tasks")
    public ResponseEntity<ResApi<List<Task>>> getAll() {
        return ResponseEntity.ok(new ResApi<>(200, "Success", sv.getAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or @secUtil.isTaskAssignedToSelf(#id, authentication)")
    @Operation(summary = "Get task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<ResApi<Task>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResApi<>(200, "Success", sv.getById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data or task is DONE"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<ResApi<Task>> update(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(new ResApi<>(200, "Task updated successfully", sv.update(id, task)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<ResApi<Void>> delete(@PathVariable Long id) {
        sv.delete(id);
        return ResponseEntity.ok(new ResApi<>(200, "Task deleted successfully", null));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('MANAGER') or @secUtil.isSelf(#userId, authentication)")
    @Operation(summary = "Get tasks by user")
    public ResponseEntity<ResApi<List<Task>>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(new ResApi<>(200, "Success", sv.getByUserId(userId)));
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('MANAGER') or @secUtil.isInProject(#projectId, authentication)")
    @Operation(summary = "Get tasks by project")
    public ResponseEntity<ResApi<List<Task>>> getByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(new ResApi<>(200, "Success", sv.getByProjectId(projectId)));
    }

    @PutMapping("/{taskId}/assign/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Assign user to task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User assigned"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<ResApi<TaskDTO>> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(new ResApi<>(200, "User assigned successfully", sv.assignTaskToUser(taskId, userId)));
    }

    @PatchMapping("/{taskId}/status")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update task status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated"),
            @ApiResponse(responseCode = "400", description = "Invalid status or task is DONE")
    })
    public ResponseEntity<ResApi<TaskDTO>> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(new ResApi<>(200, "Status updated successfully", sv.updateTaskStatus(taskId, status)));
    }
}