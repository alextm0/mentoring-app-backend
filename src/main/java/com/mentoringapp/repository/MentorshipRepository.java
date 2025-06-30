package com.mentoringapp.repository;

import com.mentoringapp.domain.Mentorship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MentorshipRepository extends JpaRepository<Mentorship, UUID> {
  Optional<Mentorship> findByMenteeId(UUID menteeId);
  List<Mentorship> findByMentorId(UUID mentorId);
  long countByMentorId(UUID mentorId);
} 