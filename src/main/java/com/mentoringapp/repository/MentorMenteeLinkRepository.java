package com.mentoringapp.repository;

import com.mentoringapp.domain.MentorMenteeLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MentorMenteeLinkRepository extends JpaRepository<MentorMenteeLink, UUID> {
  public Optional<MentorMenteeLink> findByMentorIdAndMenteeId(UUID mentorId, UUID menteeId);
}
