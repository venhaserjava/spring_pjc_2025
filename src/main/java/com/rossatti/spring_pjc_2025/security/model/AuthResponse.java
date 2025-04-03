package com.rossatti.spring_pjc_2025.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Date expiresAt;
}
