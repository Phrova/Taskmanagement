package com.example.Taskmanagement.Validation;

import com.example.Taskmanagement.Model.Task;

import java.time.LocalDate;

public class Vdtask {
    public static void tasks(Task t){

        if (t == null) {
            throw new IllegalArgumentException("Task mustn't be Null !!");
        }

        if (t.getTitle() == null || t.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title mustn't be Null !!!");
        }

        if (t.getStatus() == null) {
            throw new IllegalArgumentException("Task status mustn't be Null !!");
        }


        validateDates(t.getCreateDate(), t.getDueDate());
    }

    private static void validateDates(LocalDate createDate, LocalDate dueDate) {
        if (createDate == null || dueDate == null) {
            throw new IllegalArgumentException("Date mustn't be Null !!");
        }
        if (dueDate.isBefore(createDate)) {
            throw new IllegalArgumentException("Due date can't be before create date !!");
        }
    }

}
