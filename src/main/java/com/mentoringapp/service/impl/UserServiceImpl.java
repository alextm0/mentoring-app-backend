package com.mentoringapp.service.impl;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.domain.User;
import com.mentoringapp.exceptions.MentorshipNotFoundException;
import com.mentoringapp.exceptions.UserException;
import com.mentoringapp.exceptions.UserNotFoundException;
import com.mentoringapp.repository.MentorshipRepository;
import com.mentoringapp.repository.UserRepository;
import com.mentoringapp.service.UserService;
import jakarta.transaction.Transactional;
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
    Mentorship mentorship = mentorshipRepository.findByMenteeId(menteeId)
            .orElseThrow(() -> new UserNotFoundException(
                    String.format("Mentee with id %s has no active mentor", menteeId)
            ));

    return mentorship.getMentor();
  }

  @Override
  public List<User> getMentees(UUID mentorId) {
    userRepository.findById(mentorId)
            .orElseThrow(() -> new UserNotFoundException(
                    String.format("User with id %s not found", mentorId)
            ));

    List<Mentorship> mentorships = mentorshipRepository.findByMentorId(mentorId);
    
    return mentorships.stream()
            .map(Mentorship::getMentee)
            .toList();
  }

  @Override
  @Transactional
  public User createUser(User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new UserException("User with this email already exists");
    }

    return userRepository.save(user);
  }
}
