package com.example.Taskmanagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    private String name;
    private String description;

    @Column(name = "create_date")
    private LocalDate createDate;

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    private List<Task> tasks = new ArrayList<>();

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("projects")
    private List<User> users = new ArrayList<>();
}
