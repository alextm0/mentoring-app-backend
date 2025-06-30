package com.mentoringapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String role;
    private Integer totalXp;
    private Integer currentLevel;
} 