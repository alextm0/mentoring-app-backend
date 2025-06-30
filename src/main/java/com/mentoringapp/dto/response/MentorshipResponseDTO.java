package com.mentoringapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorshipResponseDTO {
    private UUID id;
    private UserResponseDTO mentor;
    private UserResponseDTO mentee;
    private Boolean isActive;
    private LocalDateTime createdAt;
} 