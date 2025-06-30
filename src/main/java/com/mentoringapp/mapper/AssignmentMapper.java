package com.mentoringapp.mapper;

import com.mentoringapp.domain.Assignment;
import com.mentoringapp.dto.response.GetAssignmentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssignmentMapper {
  GetAssignmentResponseDTO assignmentToGetAssignmentResponseDTO(Assignment assignment);
  Assignment getAssignmentResponseDTOToAssignment(GetAssignmentResponseDTO assignmentResponseDTO);
}
