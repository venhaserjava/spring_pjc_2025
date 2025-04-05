package com.rossatti.spring_pjc_2025.security.dtos.request;


import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}

