package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.ReqRegister;
import com.example.Taskmanagement.DTO.ResApi;
import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Service.SVuser_w3;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RCTuser {

    private final SVuser_w3 sv;

    @GetMapping
    public ResponseEntity<ResApi<List<User>>> getAll() {
        List<User> users = sv.getAll();
        ResApi<List<User>> response = new ResApi<>(200, "Success", users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResApi<User>> getById(@PathVariable Long id) {
        User user = sv.getbyId(id);
        ResApi<User> response = new ResApi<>(200, "Success", user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResApi<User>> create(@RequestBody User user) {
        User created = sv.create(user);
        ResApi<User> response = new ResApi<>(201, "User created successfully", created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResApi<User>> update(@PathVariable Long id, @RequestBody User user) {
        User updated = sv.update(id, user);
        ResApi<User> response = new ResApi<>(200, "User updated successfully", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResApi<Void>> delete(@PathVariable Long id) {
        sv.delete(id);
        ResApi<Void> response = new ResApi<>(200, "User deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ResApi<User>> register(@RequestBody ReqRegister request) {
        User created = sv.registerUser(request);
        ResApi<User> response = new ResApi<>(201, "User registered successfully", created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}