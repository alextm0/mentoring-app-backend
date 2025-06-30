package com.mentoringapp.controller;

import com.mentoringapp.domain.Assignment;
import com.mentoringapp.dto.request.CreateAssignmentRequest;
import com.mentoringapp.dto.response.GetAssignmentResponseDTO;
import com.mentoringapp.mapper.AssignmentMapper;
import com.mentoringapp.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assignments")
@Tag(name = "Assignment Management", description = "Operations for managing assignments")
public class AssignmentController {
  private final AssignmentService assignmentService;
  private final AssignmentMapper assignmentMapper;

  @Operation(summary = "Get all assignments", description = "Retrieve a list of all assignments in the system")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved assignments")
  @GetMapping
  public ResponseEntity<List<GetAssignmentResponseDTO>> getAssignments() {
    List<GetAssignmentResponseDTO> assignmentResponseDTOList = assignmentService.getAssignments()
            .stream()
            .map(assignmentMapper::assignmentToGetAssignmentResponseDTO)
            .toList();

    return ResponseEntity.ok(assignmentResponseDTOList);
  }

  @Operation(summary = "Create a new assignment")
  @ApiResponse(responseCode = "201", description = "Assignment created successfully")
  @PostMapping
  public ResponseEntity<GetAssignmentResponseDTO> createAssignment(@Valid @RequestBody CreateAssignmentRequest request) {
    Assignment assignment = assignmentService.createAssignment(request);
    GetAssignmentResponseDTO assignmentResponseDTO = assignmentMapper.assignmentToGetAssignmentResponseDTO(assignment);

    return new ResponseEntity<>(assignmentResponseDTO, HttpStatus.CREATED);
  }
}
