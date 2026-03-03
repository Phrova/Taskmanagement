package com.example.Taskmanagement.Service;

import com.example.Taskmanagement.Model.Project;
import com.example.Taskmanagement.Repo.RPproject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SVproject_w4 {

    private final RPproject repo;
    public List<Project> getAll() {
        return repo.findAll();
    }

    public Project getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found !!!"));
    }

    public Project create(Project project) {
        return repo.save(project);
    }

    public Project update(Long id, Project project) {
        Project UPDproject = getById(id);
        UPDproject.setName(project.getName());
        UPDproject.setDescription(project.getDescription());
        UPDproject.setCreateDate(project.getCreateDate());
        return repo.save(UPDproject);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
