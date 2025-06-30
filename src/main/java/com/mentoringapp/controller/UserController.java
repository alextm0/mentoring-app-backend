package com.mentoringapp.controller;

import com.mentoringapp.domain.MentorMenteeLink;
import com.mentoringapp.domain.User;
import com.mentoringapp.dto.request.CreateLinkRequestDTO;
import com.mentoringapp.dto.response.CreateLinkResponseDTO;
import com.mentoringapp.dto.response.GetMenteeListResponseDTO;
import com.mentoringapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return userService.createUser(user) != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build();
  }

  @GetMapping("/mentees/{menteeId}/mentor")
  public ResponseEntity<User> getMentor(@PathVariable UUID menteeId) {
    return ResponseEntity.ok(userService.getMentor(menteeId));
  }

  @GetMapping("/mentors/{mentorId}/mentees")
  public ResponseEntity<List<User>> getMentees(@PathVariable UUID mentorId) {
    return ResponseEntity.ok(userService.getMentees(mentorId));
  }

  @PostMapping("/mentors/{mentorId}/mentor-links")
  public ResponseEntity<MentorMenteeLink> createLink(@PathVariable UUID mentorId, @RequestBody CreateLinkRequestDTO request) {
    MentorMenteeLink mentorMenteeLink = userService.addMenteeLink(mentorId, request.getEmail());
    return ResponseEntity.ok(mentorMenteeLink);
  }
}
