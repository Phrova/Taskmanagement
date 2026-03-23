package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.ReqRegister;
import com.example.Taskmanagement.DTO.ResApi;
import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Service.SVuser_w3;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class RCTuser {

    private final SVuser_w3 sv;

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Get all users", description = "Returns list of all users")
    public ResponseEntity<ResApi<List<User>>> getAll() {
        List<User> users = sv.getAll();
        ResApi<List<User>> response = new ResApi<>(200, "Success", users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or @secUtil.isSelf(#id, authentication)")
    @Operation(summary = "Get user by ID", description = "Returns a single user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ResApi<User>> getById(@PathVariable Long id) {
        User user = sv.getbyId(id);
        ResApi<User> response = new ResApi<>(200, "Success", user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create a new user", description = "Creates a user (without registration, use /register instead)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<ResApi<User>> create(@RequestBody User user) {
        User created = sv.create(user);
        ResApi<User> response = new ResApi<>(201, "User created successfully", created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or @secUtil.isSelf(#id, authentication)")
    @Operation(summary = "Update user", description = "Update user details (username, email, password)")
    public ResponseEntity<ResApi<User>> update(@PathVariable Long id, @RequestBody User user) {
        User updated = sv.update(id, user);
        ResApi<User> response = new ResApi<>(200, "User updated successfully", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or @secUtil.isSelf(#id, authentication)")
    @Operation(summary = "Delete user", description = "Delete user by ID")
    public ResponseEntity<ResApi<Void>> delete(@PathVariable Long id) {
        sv.delete(id);
        ResApi<Void> response = new ResApi<>(200, "User deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Public registration, assigns default USER role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "Email already exists or invalid data")
    })
    public ResponseEntity<ResApi<User>> register(@RequestBody ReqRegister request) {
        User created = sv.registerUser(request);
        ResApi<User> response = new ResApi<>(201, "User registered successfully", created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}