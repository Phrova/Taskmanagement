package com.example.Taskmanagement.Model;

import com.example.Taskmanagement.Enum.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "task_id")
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(length = 500)
        private String description;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private TaskStatus status;

        @Column(name = "create_date", nullable = false)
        private LocalDate createDate;

        @Column(name = "due_date")
        private LocalDate dueDate;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonIgnoreProperties({"tasks", "projects"})
        private User user;

        @ManyToOne
        @JoinColumn(name = "project_id")
        @JsonIgnoreProperties({"tasks", "users"})
        private Project project;

//    public void setProject(Project project) {
//        this.projects = project;
//    }
//    public void setUser(User user) {
//        this.users = user;
//    }

}
