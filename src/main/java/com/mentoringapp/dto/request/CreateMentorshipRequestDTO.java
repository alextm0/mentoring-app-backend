package com.mentoringapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMentorshipRequestDTO {
    @NotNull(message = "Mentor ID must not be null")
    private UUID mentorId;
    
    @NotBlank(message = "Mentee email must not be blank")
    @Email(message = "Mentee email must be valid")
    private String menteeEmail;
} 