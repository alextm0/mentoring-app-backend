package com.mentoringapp.service;

import com.mentoringapp.domain.Assignment;
import com.mentoringapp.dto.request.CreateAssignmentRequest;

import java.util.List;
import java.util.UUID;

public interface AssignmentService {
  Assignment getAssignment(UUID assignmentId);
  List<Assignment> getAssignments();

  List<Assignment> getAssignmentsByMenteeId(UUID menteeId);
  List<Assignment> getAssignmentsByMentorId(UUID mentorId);

  Assignment createAssignment(CreateAssignmentRequest request);
  void deleteAssignment(UUID userId, UUID assignmentId);
}
