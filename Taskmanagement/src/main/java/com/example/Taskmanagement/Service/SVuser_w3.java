package com.example.Taskmanagement.Service;

import com.example.Taskmanagement.DTO.ReqRegister;
import com.example.Taskmanagement.Model.Role;
import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Repo.RProle;
import com.example.Taskmanagement.Repo.RPuser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SVuser_w3 {

    private final RPuser userRepo;
    private final RProle roleRepo;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getbyId(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User create(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Role userRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRoles(List.of(userRole));

        return userRepo.save(user);
    }

    public User update(Long id, User user) {
        User existing = getbyId(id);
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepo.save(existing);
    }

    public void delete(Long id) {
        User user = getbyId(id);

        user.getTasks().forEach(task -> task.setUser(null));
        userRepo.save(user);

        userRepo.deleteById(id);
    }

    public User registerUser(ReqRegister request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRoles(List.of(userRole));

        return userRepo.save(user);
    }
}