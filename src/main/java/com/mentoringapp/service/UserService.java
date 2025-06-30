package com.mentoringapp.service;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
  public List<User> getUsers();
  public User getMentor(UUID menteeId);
  public List<User> getMentees(UUID mentorId);
  public List<User> getUsersByRole(String role);

  public User createUser(User user);

  public Mentorship assignMentorToMentee(UUID mentorId, String menteeEmail);
  public void deleteMentorship(UUID mentorId, UUID menteeId);
}
