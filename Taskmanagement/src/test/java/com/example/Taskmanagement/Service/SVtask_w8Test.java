package com.example.Taskmanagement.Service;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import com.example.Taskmanagement.Security.CusUserDetail;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.Taskmanagement.DTO.TaskDTO;
import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.ExceptionHandler.CSVDexception;
import com.example.Taskmanagement.ExceptionHandler.CSresourceNotfound;
import com.example.Taskmanagement.Model.Project;
import com.example.Taskmanagement.Model.Task;
import com.example.Taskmanagement.Model.User;
import com.example.Taskmanagement.Repo.RPproject;
import com.example.Taskmanagement.Repo.RPtask;
import com.example.Taskmanagement.Repo.RPuser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SVtask_w8Test {

    @Mock private RPtask repo;
    @Mock private RPuser userRepo;
    @Mock private RPproject projectRepo;
    @InjectMocks private SVtask_w4 sv;

    private TaskDTO dto;
    private User user;
    private Project project;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.TODO);
        task.setCreateDate(LocalDate.now());
        task.setUser(user);
        task.setProject(project);

        dto = new TaskDTO();
        dto.setTitle("Test Task");
        dto.setDescription("Desc");
        dto.setDueDate(LocalDate.now().plusDays(1));
        dto.setUserId(1L);
        dto.setProjectId(1L);
    }

    @Test
    void createTask_success() {

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepo.findById(1L)).thenReturn(Optional.of(project));
        when(repo.save(any(Task.class))).thenReturn(task);

        TaskDTO result = sv.createTask(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getProjectId()).isEqualTo(1L);
        verify(repo).save(any(Task.class));
    }

    @Test
    void createTask_titleEmpty_throws() {

        dto.setTitle("");
        assertThatThrownBy(() -> sv.createTask(dto))
                .isInstanceOf(CSVDexception.class)
                .hasMessageContaining("Title cannot be empty");
    }

    @Test
    void createTask_dueDatePast_throws() {

        dto.setDueDate(LocalDate.now().minusDays(1));
        assertThatThrownBy(() -> sv.createTask(dto))
                .isInstanceOf(CSVDexception.class)
                .hasMessageContaining("Due date must be today or later");
    }

    @Test
    void createTask_projectNotFound_throws() {

        when(projectRepo.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> sv.createTask(dto))
                .isInstanceOf(com.example.Taskmanagement.ExceptionHandler.CSresourceNotfound.class)
                .hasMessageContaining("Project not found");
    }

    @Test
    void createTask_userNotFound_throws() {

        when(projectRepo.findById(1L)).thenReturn(Optional.of(project));
        when(userRepo.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> sv.createTask(dto))
                .isInstanceOf(com.example.Taskmanagement.ExceptionHandler.CSresourceNotfound.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void assignTaskToUser_success() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setStatus(TaskStatus.TODO);
        existingTask.setProject(project);

        User newUser = new User();
        newUser.setId(2L);

        project.setUsers(List.of(newUser));

        when(repo.findById(1L)).thenReturn(Optional.of(existingTask));
        when(userRepo.findById(2L)).thenReturn(Optional.of(newUser));
        when(projectRepo.findById(1L)).thenReturn(Optional.of(project));
        when(repo.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        TaskDTO result = sv.assignTaskToUser(1L, 2L);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(2L);
        verify(repo).save(any(Task.class));
    }

    @Test
    void assignTaskToUser_userNotInProject_throws() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setStatus(TaskStatus.TODO);
        existingTask.setProject(project);

        User newUser = new User();
        newUser.setId(2L);
        project.setUsers(List.of());

        when(repo.findById(1L)).thenReturn(Optional.of(existingTask));
        when(userRepo.findById(2L)).thenReturn(Optional.of(newUser));
        when(projectRepo.findById(1L)).thenReturn(Optional.of(project));

        assertThatThrownBy(() -> sv.assignTaskToUser(1L, 2L))
                .isInstanceOf(CSVDexception.class)
                .hasMessageContaining("User does not belong to the project");
    }

    @Test
    void assignTaskToUser_taskDone_throws() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setStatus(TaskStatus.DONE);

        when(repo.findById(1L)).thenReturn(Optional.of(existingTask));

        assertThatThrownBy(() -> sv.assignTaskToUser(1L, 2L))
                .isInstanceOf(CSVDexception.class)
                .hasMessageContaining("Cannot assign user to a DONE task");
    }

    @Test
    void getById_taskNotFound_throws() {

        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> sv.getById(99L))
                .isInstanceOf(CSresourceNotfound.class)
                .hasMessageContaining("Task not found with id: 99");
    }

    @Test
    void update_taskDone_throws() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setStatus(TaskStatus.DONE);
        when(repo.findById(1L)).thenReturn(Optional.of(existingTask));

        Task updateData = new Task();
        updateData.setTitle("New Title");

        assertThatThrownBy(() -> sv.update(1L, updateData))
                .isInstanceOf(CSVDexception.class)
                .hasMessageContaining("Cannot update a DONE task");
    }

    @Test
    void delete_success() {

        Task existingTask = new Task();
        existingTask.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(existingTask));
        doNothing().when(repo).deleteById(1L);

        sv.delete(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    void delete_taskNotFound_throws() {

        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> sv.delete(99L))
                .isInstanceOf(CSresourceNotfound.class)
                .hasMessageContaining("Task not found with id: 99");
    }

    private Task createTaskWithId(Long id, TaskStatus status, Project proj, User usr) {
        Task t = new Task();
        t.setId(id);
        t.setStatus(status);
        t.setProject(proj);
        t.setUser(usr);
        t.setTitle("Sample");
        t.setCreateDate(LocalDate.now());
        return t;
    }

    private User createUser(Long id) {
        User u = new User();
        u.setId(id);
        u.setUsername("user" + id);
        return u;
    }

    private Project createProjectWithUsers(Long id, List<User> users) {
        Project p = new Project();
        p.setId(id);
        p.setName("Project" + id);
        p.setUsers(users);
        return p;
    }


}