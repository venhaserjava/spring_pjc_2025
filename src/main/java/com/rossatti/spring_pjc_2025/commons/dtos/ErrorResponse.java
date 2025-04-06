package com.rossatti.spring_pjc_2025.commons.dtos;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ErrorResponse {

    private int status;
    private String message;
    private String error;
    private String cause;
    private LocalDateTime timestamp;    
    
}
