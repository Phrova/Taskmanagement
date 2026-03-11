package com.example.Taskmanagement.DTO;

import lombok.Data;

@Data
public class ReqLogin {
    private String email;
    private String password;
}