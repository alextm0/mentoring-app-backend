package com.mentoringapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMenteeListResponseDTO {
  private String name;
  private String email;
  private String role;
  private String totalXp;
  private String currentLevel;
  // TODO: Mentor information
}
