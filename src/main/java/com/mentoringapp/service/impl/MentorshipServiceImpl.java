package com.mentoringapp.service.impl;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.domain.User;
import com.mentoringapp.exceptions.InvalidMentorshipException;
import com.mentoringapp.exceptions.MentorshipAlreadyExistsException;
import com.mentoringapp.exceptions.MentorshipNotFoundException;
import com.mentoringapp.exceptions.MentorshipPermissionException;
import com.mentoringapp.exceptions.UserNotFoundException;
import com.mentoringapp.repository.MentorshipRepository;
import com.mentoringapp.repository.UserRepository;
import com.mentoringapp.service.MentorshipService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorshipServiceImpl implements MentorshipService {
  private final UserRepository userRepository;
  private final MentorshipRepository mentorshipRepository;

  @Override
  @Transactional
  public Mentorship assignMentorToMentee(UUID mentorId, String menteeEmail) {
    User mentor = userRepository.findById(mentorId)
            .orElseThrow(() -> new UserNotFoundException("Mentor not found"));
    User mentee = userRepository.findByEmail(menteeEmail)
            .orElseThrow(() -> new UserNotFoundException("Mentee not found"));
    if(mentor.getId().equals(mentee.getId())) {
      throw new InvalidMentorshipException("Mentor and mentee cannot be the same");
    }
    if (!"MENTOR".equalsIgnoreCase(mentor.getRole())) {
      throw new InvalidMentorshipException("User " + mentor.getEmail() + " is not a mentor.");
    }
    if (!"MENTEE".equalsIgnoreCase(mentee.getRole())) {
      throw new InvalidMentorshipException("User " + mentee.getEmail() + " is not a mentee.");
    }
    Optional<Mentorship> existingMentorship = mentorshipRepository.findByMenteeId(mentee.getId());
    if(existingMentorship.isPresent()) {
      throw new MentorshipAlreadyExistsException("This mentee is already linked to a mentor");
    }
    Mentorship mentorship = new Mentorship();
    mentorship.setMentor(mentor);
    mentorship.setMentee(mentee);
    mentorship.setIsActive(true);
    return mentorshipRepository.save(mentorship);
  }

  @Override
  @Transactional
  public void removeMentorship(UUID mentorshipId) {
    Mentorship mentorship = mentorshipRepository.findById(mentorshipId)
            .orElseThrow(() -> new MentorshipNotFoundException("Mentorship not found"));

    // Simply delete the mentorship - authorization will be handled by auth context in the future
    mentorshipRepository.delete(mentorship);
  }

  @Override
  public List<Mentorship> getAllMentorships() {
    return mentorshipRepository.findAll();
  }
} 