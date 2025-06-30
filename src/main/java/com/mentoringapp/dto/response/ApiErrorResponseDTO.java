package com.mentoringapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponseDTO {
    private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp;
    
    public ApiErrorResponseDTO(String message, String error, int status) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
} 