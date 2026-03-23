package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.ProjectDTO;
import com.example.Taskmanagement.DTO.ResApi;
import com.example.Taskmanagement.Service.SVproject_w4;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "APIs for managing projects")
public class RCLproject {

    private final SVproject_w4 sv;

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Get all projects")
    public ResponseEntity<ResApi<List<ProjectDTO>>> getAll() {
        return ResponseEntity.ok(new ResApi<>(200, "Success", sv.getAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or @secUtil.isInProject(#id, authentication)")
    @Operation(summary = "Get project by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ResApi<ProjectDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResApi<>(200, "Success", sv.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<ResApi<ProjectDTO>> create(@RequestBody ProjectDTO dto) {
        return new ResponseEntity<>(new ResApi<>(201, "Project created successfully", sv.create(dto)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update project")
    public ResponseEntity<ResApi<ProjectDTO>> update(@PathVariable Long id, @RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(new ResApi<>(200, "Project updated successfully", sv.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete project")
    public ResponseEntity<ResApi<Void>> delete(@PathVariable Long id) {
        sv.delete(id);
        return ResponseEntity.ok(new ResApi<>(200, "Project deleted successfully", null));
    }

    @PostMapping("/{projectId}/users/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Add user to project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added"),
            @ApiResponse(responseCode = "404", description = "Project or user not found"),
            @ApiResponse(responseCode = "400", description = "User already in project")
    })
    public ResponseEntity<ResApi<ProjectDTO>> addUser(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(new ResApi<>(200, "User added to project", sv.addUser(projectId, userId)));
    }

    @DeleteMapping("/{projectId}/users/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Remove user from project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User removed"),
            @ApiResponse(responseCode = "404", description = "Project or user not found"),
            @ApiResponse(responseCode = "400", description = "User not in project")
    })
    public ResponseEntity<ResApi<ProjectDTO>> removeUser(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(new ResApi<>(200, "User removed from project", sv.removeUser(projectId, userId)));
    }
}