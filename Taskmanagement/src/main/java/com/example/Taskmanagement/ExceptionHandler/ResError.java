package com.example.Taskmanagement.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}