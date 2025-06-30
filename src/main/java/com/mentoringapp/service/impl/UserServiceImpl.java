package com.mentoringapp.service.impl;

import com.mentoringapp.domain.MentorMenteeLink;
import com.mentoringapp.domain.User;
import com.mentoringapp.exceptions.LinkNotFoundException;
import com.mentoringapp.exceptions.UserException;
import com.mentoringapp.exceptions.UserNotFoundException;
import com.mentoringapp.repository.MentorMenteeLinkRepository;
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
  private final MentorMenteeLinkRepository mentorMenteeLinkRepository;

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getMentor(UUID menteeId) {
    User mentee = userRepository.findById(menteeId)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", menteeId)));

    MentorMenteeLink link = mentee.getMentorLinks().stream()
            .filter(MentorMenteeLink::getIsActive)
            .findFirst()
            .orElseThrow(() -> new UserNotFoundException(String.format("Mentee with id %s has no mentor", menteeId)));

    return link.getMentor();
  }

  @Override
  public List<User> getMentees(UUID mentorId) {
    User mentor = userRepository.findById(mentorId)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", mentorId)));

    List<User> mentees = mentor.getMenteeLinks()
            .stream()
            .filter(MentorMenteeLink::getIsActive)
            .map(MentorMenteeLink::getMentee)
            .toList();

    return mentees;
  }

  @Override
  public User createUser(User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new UserException("User with this email already exists");
    }

    return userRepository.save(user);
  }

  @Override
  public MentorMenteeLink addMenteeLink(UUID mentorId, String email) {
    User mentor = userRepository.findById(mentorId)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", mentorId)));

    User mentee = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));

    MentorMenteeLink link = new MentorMenteeLink();
    link.setMentor(mentor);
    link.setMentee(mentee);
    link.setIsActive(true);

    mentor.getMenteeLinks().add(link);
    mentee.getMentorLinks().add(link);
    userRepository.save(mentor);

    return link;
  }

  @Override
  public void deleteMenteeLink(UUID mentorId, UUID menteeId) {
    MentorMenteeLink link = mentorMenteeLinkRepository.findByMentorIdAndMenteeId(mentorId, menteeId)
            .orElseThrow(() -> new LinkNotFoundException(String.format("Link between user %s and user %s not found", mentorId, menteeId)));

    link.setIsActive(false);
    userRepository.save(link.getMentor());
    userRepository.save(link.getMentee());
    mentorMenteeLinkRepository.save(link);
  }
}
