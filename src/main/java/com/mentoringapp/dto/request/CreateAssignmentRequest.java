package com.mentoringapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssignmentRequest {
  @NotBlank
  @Length(min = 1, max = 100)
  private String title;

  @Length(max = 255)
  private String description = "no description";

  @NotNull
  private UUID mentorId;

  @NotNull
  private UUID menteeId;
}