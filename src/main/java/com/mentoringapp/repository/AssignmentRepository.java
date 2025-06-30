package com.mentoringapp.repository;

import com.mentoringapp.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
  List<Assignment> findByMenteeId(UUID menteeId);
  List<Assignment> findByMentorId(UUID mentorId);
}
