package com.mentoringapp.service;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.domain.User;
import com.mentoringapp.domain.UserRole;

import java.util.List;
import java.util.UUID;

public interface UserService {
  List<User> getUsers();
  User getMentor(UUID menteeId);
  List<User> getMentees(UUID mentorId);
  List<User> getUsersByRole(UserRole role);

  User createUser(User user);
}
