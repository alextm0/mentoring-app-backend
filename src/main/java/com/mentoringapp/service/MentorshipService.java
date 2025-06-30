package com.mentoringapp.service;

import com.mentoringapp.domain.Mentorship;

import java.util.List;
import java.util.UUID;

public interface MentorshipService {
  Mentorship assignMentorToMentee(UUID mentorId, String menteeEmail);
  void removeMentorship(UUID mentorshipId);
  List<Mentorship> getAllMentorships();
} 