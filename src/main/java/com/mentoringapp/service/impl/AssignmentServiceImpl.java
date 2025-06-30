package com.mentoringapp.service.impl;

import com.mentoringapp.domain.Assignment;
import com.mentoringapp.domain.AssignmentStatus;
import com.mentoringapp.domain.User;
import com.mentoringapp.dto.request.CreateAssignmentRequest;
import com.mentoringapp.exceptions.AssignmentNotFoundException;
import com.mentoringapp.exceptions.AssignmentPermissionException;
import com.mentoringapp.exceptions.UserNotFoundException;
import com.mentoringapp.repository.AssignmentRepository;
import com.mentoringapp.repository.UserRepository;
import com.mentoringapp.service.AssignmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
  private final UserRepository userRepository;
  private final AssignmentRepository assignmentRepository;

  @Override
  public List<Assignment> getAssignments() {
    return assignmentRepository.findAll();
  }

  @Override
  public Assignment getAssignment(UUID assignmentId) {
    return assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new AssignmentNotFoundException("Assignment with id " + assignmentId + " not found"));
  }

  @Override
  public List<Assignment> getAssignmentsByMenteeId(UUID menteeId) {
    return assignmentRepository.findByMenteeId(menteeId);
  }

  @Override
  public List<Assignment> getAssignmentsByMentorId(UUID mentorId) {
    return assignmentRepository.findByMentorId(mentorId);
  }

  @Override
  @Transactional
  public Assignment createAssignment(CreateAssignmentRequest request) {
    // fetch mentor and mentee
    User mentor = userRepository.findById(request.getMentorId())
            .orElseThrow(() -> new UserNotFoundException("Mentor with id " + request.getMentorId() + " not found"));

    User mentee = userRepository.findById(request.getMenteeId())
            .orElseThrow(() -> new UserNotFoundException("Mentee with id " + request.getMenteeId() + " not found"));

    if (!"MENTOR".equalsIgnoreCase(mentor.getRole())) {
      throw new AssignmentPermissionException("User " + mentor.getEmail() + " is not a mentor.");
    }

    if (!"MENTEE".equalsIgnoreCase(mentee.getRole())) {
      throw new AssignmentPermissionException("User " + mentee.getEmail() + " is not a mentee.");
    }

    Assignment assignment = new Assignment();
    assignment.setTitle(request.getTitle());
    assignment.setDescription(request.getDescription());
    assignment.setMentor(mentor);
    assignment.setMentee(mentee);
    assignment.setStatus(AssignmentStatus.PENDING);

    return assignmentRepository.save(assignment);
  }

  @Override
  @Transactional
  public void deleteAssignment(UUID userId, UUID assignmentId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

    if (!"MENTOR".equalsIgnoreCase(user.getRole())) {
      throw new AssignmentPermissionException("User " + user.getEmail() + " is not a mentor.");
    }

    assignmentRepository.deleteById(assignmentId);
  }
}
