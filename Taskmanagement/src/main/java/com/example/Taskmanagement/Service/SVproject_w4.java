package com.example.Taskmanagement.Service;

import com.example.Taskmanagement.DTO.ProjectDTO;
import com.example.Taskmanagement.Model.Project;
import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Repo.RPproject;
import com.example.Taskmanagement.Repo.RPuser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SVproject_w4 {

    private final RPproject repo;
    private final RPuser userRepo;

    private ProjectDTO toDTO(Project p) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setCreateDate(p.getCreateDate());
        if (p.getUsers() != null) {
            dto.setUserIds(p.getUsers().stream().map(User::getId).collect(Collectors.toList()));
            dto.setMemberCount(p.getUsers().size());
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDTO getById(Long id) {
        Project p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return toDTO(p);
    }

    @Transactional
    public ProjectDTO create(ProjectDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new RuntimeException("Project name cannot be empty");
        }
        Project p = new Project();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setCreateDate(dto.getCreateDate() != null ? dto.getCreateDate() : LocalDate.now());
        return toDTO(repo.save(p));
    }

    @Transactional
    public ProjectDTO update(Long id, ProjectDTO dto) {
        Project p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        if (dto.getName() != null && !dto.getName().isBlank()) p.setName(dto.getName());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getCreateDate() != null) p.setCreateDate(dto.getCreateDate());
        return toDTO(repo.save(p));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Project not found with id: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional
    public ProjectDTO addUser(Long projectId, Long userId) {
        Project project = repo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.getProjects().size();

        boolean alreadyMember = user.getProjects().stream()
                .anyMatch(p -> p.getId().equals(projectId));
        if (alreadyMember) {
            throw new RuntimeException("User is already a member of this project");
        }

        user.getProjects().add(project);
        userRepo.save(user);

        project = repo.findById(projectId).get();
        project.getUsers().size();
        return toDTO(project);
    }

    @Transactional
    public ProjectDTO removeUser(Long projectId,     Long userId) {
        Project project = repo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.getProjects().size();

        boolean wasMember = user.getProjects().removeIf(p -> p.getId().equals(projectId));
        if (!wasMember) {
            throw new RuntimeException("User is not a member of this project");
        }

        userRepo.save(user);
        project = repo.findById(projectId).get();
        project.getUsers().size();
        return toDTO(project);
    }
}
