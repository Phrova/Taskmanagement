package com.example.Taskmanagement.DTO;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate createDate;
    private List<Long> userIds;
    private int memberCount;
}