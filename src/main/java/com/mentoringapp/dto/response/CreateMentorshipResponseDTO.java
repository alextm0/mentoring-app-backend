package com.mentoringapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMentorshipResponseDTO {
    private UUID id;
    private GetUserResponseDTO mentor;
    private GetUserResponseDTO mentee;
    private LocalDateTime createdAt;
} 