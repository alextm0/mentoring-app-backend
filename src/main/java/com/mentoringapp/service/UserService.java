package com.mentoringapp.service;

import com.mentoringapp.domain.MentorMenteeLink;
import com.mentoringapp.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
  public List<User> getUsers();
  public User getMentor(UUID menteeId);
  public List<User> getMentees(UUID mentorId);

  public User createUser(User user);

  public MentorMenteeLink addMenteeLink(UUID mentorId, String email);
  public void deleteMenteeLink(UUID mentorId, UUID menteeId);
}
