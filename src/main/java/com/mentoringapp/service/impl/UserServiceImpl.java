package com.mentoringapp.service.impl;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.domain.User;
import com.mentoringapp.exceptions.MentorshipNotFoundException;
import com.mentoringapp.exceptions.UserException;
import com.mentoringapp.exceptions.UserNotFoundException;
import com.mentoringapp.repository.MentorshipRepository;
import com.mentoringapp.repository.UserRepository;
import com.mentoringapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final MentorshipRepository mentorshipRepository;

  @Override
  public List<User> getUsersByRole(String role) {
    return userRepository.findByRole(role);
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getMentor(UUID menteeId) {
    Mentorship mentorship = mentorshipRepository.findByMenteeIdAndIsActive(menteeId, true)
            .orElseThrow(() -> new UserNotFoundException(String.format("Mentee with id %s has no active mentor", menteeId)));

    return mentorship.getMentor();
  }

  @Override
  public List<User> getMentees(UUID mentorId) {
    userRepository.findById(mentorId)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", mentorId)));

    List<Mentorship> mentorships = mentorshipRepository.findByMentorIdAndIsActive(mentorId, true);
    
    return mentorships.stream()
            .map(Mentorship::getMentee)
            .toList();
  }

  @Override
  public User createUser(User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new UserException("User with this email already exists");
    }

    return userRepository.save(user);
  }

  @Override
  public Mentorship assignMentorToMentee(UUID mentorId, String menteeEmail) {
    User mentor = userRepository.findById(mentorId)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", mentorId)));
    User mentee = userRepository.findByEmail(menteeEmail)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", menteeEmail)));
    
    // Check if mentee already has an active mentor
    if (mentorshipRepository.findByMenteeIdAndIsActive(mentee.getId(), true).isPresent()) {
      throw new UserException("This mentee already has an active mentor");
    }
    
    Mentorship mentorship = new Mentorship();
    mentorship.setMentor(mentor);
    mentorship.setMentee(mentee);
    mentorship.setIsActive(true);
    
    return mentorshipRepository.save(mentorship);
  }

  @Override
  public void deleteMentorship(UUID mentorshipId, UUID menteeId) {
    Mentorship mentorship = mentorshipRepository.findByMentorIdAndMenteeId(mentorshipId, menteeId)
            .orElseThrow(() -> new MentorshipNotFoundException(String.format("Mentorship between mentor %s and mentee %s not found", mentorshipId, menteeId)));

    // Simply delete the mentorship
    mentorshipRepository.delete(mentorship);
  }
}
