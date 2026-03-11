package com.example.Taskmanagement.DTO;

import com.example.Taskmanagement.Enum.TaskStatus;
import com.example.Taskmanagement.Validation.VdfutureDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title can't be blank")
    @Size(min = 3, max = 20, message = "Title must be between 3 & 20 characters")
    private String title;

    @Size(max = 400, message = "Description max 400 characters")
    private String description;

    @NotNull(message = "Status can't be null")
    private TaskStatus status;

        private LocalDate createDate;

    @VdfutureDate(message = "Due date must be today or later")
    private LocalDate dueDate;

    private Long userId;

    private Long projectId;
}