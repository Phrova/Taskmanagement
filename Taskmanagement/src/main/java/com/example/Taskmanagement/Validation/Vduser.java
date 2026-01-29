package com.example.Taskmanagement.Validation;

import com.example.Taskmanagement.Model.User;

import java.util.regex.Pattern;

public class Vduser {

    private static final String email_format =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static void user(User u){
        if(u == null){
            throw new IllegalArgumentException("User mustn't be Null !!");
        }

        if(u.getUsername() == null || u.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("Username mustn't be Null !!");
        }

        if(u.getPassword() == null || u.getPassword().length() < 6){
            throw new IllegalArgumentException("Password must have 6 characters !!");
        }

        String email = u.getEmail();

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email mustn't be Null !!");
        }

        if (!Pattern.matches(email_format, email)) {
            throw new IllegalArgumentException("Email format is Invalid !!");
        }
    }
}
