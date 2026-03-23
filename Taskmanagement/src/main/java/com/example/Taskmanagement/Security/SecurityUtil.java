package com.example.Taskmanagement.Security;

import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Model.Task;
import com.example.Taskmanagement.Model.Project;
import com.example.Taskmanagement.Repo.RPuser;
import com.example.Taskmanagement.Repo.RPtask;
import com.example.Taskmanagement.Repo.RPproject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("secUtil")
public class SecurityUtil {

    @Autowired private RPuser userRepo;
    @Autowired private RPtask taskRepo;
    @Autowired private RPproject projectRepo;

    public boolean isSelf(Long id, Authentication auth) {
        User user = userRepo.findByEmail(auth.getName()).orElse(null);
        return user != null && user.getId().equals(id);
    }

    public boolean isTaskAssignedToSelf(Long taskId, Authentication auth) {
        User user = userRepo.findByEmail(auth.getName()).orElse(null);
        if (user == null) return false;
        Task task = taskRepo.findById(taskId).orElse(null);
        if (task == null || task.getUser() == null) return false;
        return task.getUser().getId().equals(user.getId());
    }

    public boolean isInProject(Long projectId, Authentication auth) {
        User user = userRepo.findByEmail(auth.getName()).orElse(null);
        if (user == null) return false;
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project == null) return false;
        return project.getUsers().stream()
                .anyMatch(u -> u.getId().equals(user.getId()));
    }

    public static CusUserDetail getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof CusUserDetail) ? (CusUserDetail) principal : null;
    }

    public static Long getCurrentUserId() {
        CusUserDetail user = getCurrentUser();
        return user != null ? user.getId() : null;
    }
}