package com.mentoringapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignmentResponseDTO {
  private UUID id;
  private String title;
  private String description;
  private String status;
  private Integer xpReward;
  private GetUserResponseDTO mentor;
  private GetUserResponseDTO mentee;

  // TODO: problem list
  // TODO: created time and deadline
}
