package com.example.Taskmanagement.DTO;

import lombok.Data;

@Data
public class ReqRegister {
    private String username;
    private String password;
    private String email;
}