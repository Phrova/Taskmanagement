package com.example.Taskmanagement.Service;

import com.example.Taskmanagement.DTO.TaskDTO;
import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.ExceptionHandler.CSresourceNotfound;
import com.example.Taskmanagement.ExceptionHandler.CSVDexception;
import com.example.Taskmanagement.Model.Project;
import com.example.Taskmanagement.Model.Task;
import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Repo.RPproject;
import com.example.Taskmanagement.Repo.RPtask;
import com.example.Taskmanagement.Repo.RPuser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import com.example.Taskmanagement.Security.SecurityUtil;
import com.example.Taskmanagement.Security.CusUserDetail;

@Service
@RequiredArgsConstructor
public class SVtask_w4 {

    private final RPtask repo;
    private final RPuser userRepo;
    private final RPproject projectRepo;

    public List<Task> getAll() {
        CusUserDetail user = SecurityUtil.getCurrentUser();

        if (user == null) throw new RuntimeException("Not authenticated");

        boolean isManager = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));

        if (isManager) {
            return repo.findAll();
        } else {
            return repo.findByUser_Id(user.getId());
        }
    }

    public Task getById(Long id) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new CSresourceNotfound("Task not found with id: " + id));
        CusUserDetail user = SecurityUtil.getCurrentUser();

        if (user == null) throw new RuntimeException("Not authenticated");

        boolean isManager = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));

        if (!isManager && !task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only view your own tasks");
        }
        return task;
    }

    public TaskDTO createTask(TaskDTO dto) {

        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new CSVDexception("Title cannot be empty");
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        } else {
            task.setStatus(TaskStatus.TODO);
        }

        task.setCreateDate(LocalDate.now());

        if (dto.getDueDate() != null) {
            if (dto.getDueDate().isBefore(LocalDate.now())) {
                throw new CSVDexception("Due date must be today or later");
            }
            task.setDueDate(dto.getDueDate());
        }

        if (dto.getProjectId() != null) {
            Project project = projectRepo.findById(dto.getProjectId())
                    .orElseThrow(() -> new CSresourceNotfound("Project not found with id: " + dto.getProjectId()));
            task.setProject(project);
        } else {
            task.setProject(null);
        }

        if (dto.getUserId() != null) {
            User user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new CSresourceNotfound("User not found with id: " + dto.getUserId()));
            task.setUser(user);
        } else {
            task.setUser(null);
        }

        Task saved = repo.save(task);
        return convertToDTO(saved);
    }

    public Task update(Long id, Task task) {
        Task existing = getById(id);

        if (existing.getStatus() == TaskStatus.DONE) {
            throw new CSVDexception("Cannot update a DONE task");
        }

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        if (task.getStatus() != null) {
            existing.setStatus(task.getStatus());
        }
        existing.setDueDate(task.getDueDate());

        return repo.save(existing);
    }

    public void delete(Long id) {

        getById(id);
        repo.deleteById(id);
    }

    public List<Task> getByUserId(Long userId) {

        if (!userRepo.existsById(userId)) {
            throw new CSresourceNotfound("User not found with id: " + userId);
        }
        return repo.findByUser_Id(userId);
    }

    public List<Task> getByProjectId(Long projectId) {
        if (!projectRepo.existsById(projectId)) {
            throw new CSresourceNotfound("Project not found with id: " + projectId);
        }
        return repo.findByProject_Id(projectId);
    }

    public TaskDTO assignTaskToUser(Long taskId, Long userId) {
        Task task = getById(taskId);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CSresourceNotfound("User not found with id: " + userId));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new CSVDexception("Cannot assign user to a DONE task");
        }


        if (task.getProject() != null) {
            Project project = projectRepo.findById(task.getProject().getId())
                    .orElseThrow(() -> new CSresourceNotfound("Project not found"));

            boolean userInProject = project.getUsers().stream()
                    .anyMatch(u -> u.getId().equals(userId));

            if (!userInProject) {
                throw new CSVDexception("User does not belong to the project of this task");
            }
        }

        task.setUser(user);
        Task saved = repo.save(task);
        return convertToDTO(saved);
    }

    public TaskDTO updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task task = getById(taskId);

        if (task.getStatus() == TaskStatus.DONE) {
            throw new CSVDexception("Cannot change status of a DONE task");
        }

        task.setStatus(newStatus);
        Task saved = repo.save(task);
        return convertToDTO(saved);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreateDate(task.getCreateDate());
        dto.setDueDate(task.getDueDate());
        if (task.getUser() != null) {
            dto.setUserId(task.getUser().getId());
        }
        if (task.getProject() != null) {
            dto.setProjectId(task.getProject().getId());
        }
        return dto;
    }
}