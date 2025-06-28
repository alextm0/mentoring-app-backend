package com.mentoringapp.service.impl;

import com.mentoringapp.domain.User;
import com.mentoringapp.repository.UserRepository;
import com.mentoringapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }
}
