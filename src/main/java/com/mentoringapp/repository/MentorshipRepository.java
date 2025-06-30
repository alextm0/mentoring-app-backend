package com.mentoringapp.repository;

import com.mentoringapp.domain.Mentorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentorshipRepository extends JpaRepository<Mentorship, UUID> {
  Optional<Mentorship> findByMenteeId(UUID menteeId);
  List<Mentorship> findByMentorId(UUID mentorId);
}