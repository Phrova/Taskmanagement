package com.example.Taskmanagement.RController;

import com.example.Taskmanagement.DTO.TaskDTO;
import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Service.SVtask_w4;
import com.example.Taskmanagement.Service.SVuser_w3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> getAll() {
        return sv.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return sv.getbyId(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return sv.create(user);
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        return sv.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sv.delete(id);
    }
}

