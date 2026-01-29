package com.example.Taskmanagement.Service;

import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Repo.RPuser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SVuser_w3 {

    private final RPuser repouser;

    public List<User> getAll(){
        return repouser.findAll();
    }

    public User getbyId(Long id){
        return repouser.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Can't find User !!!"));
    }

    public User create(User user) {
        if (repouser.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists !!!");
        }
        return repouser.save(user);
    }

    public User update(Long id, User updated) {
        User existing = getbyId(id);

        existing.setUsername(updated.getUsername());
        existing.setPassword(updated.getPassword());
        existing.setEmail(updated.getEmail());
        existing.setUserRole(updated.getUserRole());

        return repouser.save(existing);
    }

    public void delete(Long id) {
        User user = getbyId(id);
        repouser.delete(user);
    }
}
