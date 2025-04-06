package com.rossatti.spring_pjc_2025.security.dtos.response;

import java.util.Date;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
//    private String accessToken;
    private Date expiresAt;
    private String refreshToken;

}
