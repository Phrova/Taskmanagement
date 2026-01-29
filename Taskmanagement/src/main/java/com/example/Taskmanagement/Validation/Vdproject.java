package com.example.Taskmanagement.Validation;

import com.example.Taskmanagement.Model.Project;

import java.time.LocalDate;

public class Vdproject {

    public static void project(Project p) {
        if (p == null) {
            throw new IllegalArgumentException("Project mustnt be Null !!");
        }

        validateName(p.getName());
        validateCreateDate(p.getCreateDate());
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name mustn't be Null !!");
        }
    }

    private static void validateCreateDate(LocalDate createDate) {
        if (createDate == null) {
            throw new IllegalArgumentException("Project create date mustn't be Null !!");
        }
    }
}
