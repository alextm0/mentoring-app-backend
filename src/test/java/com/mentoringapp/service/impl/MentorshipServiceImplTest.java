package com.mentoringapp.service.impl;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.domain.User;
import com.mentoringapp.exceptions.InvalidMentorshipException;
import com.mentoringapp.exceptions.MentorshipAlreadyExistsException;
import com.mentoringapp.exceptions.UserNotFoundException;
import com.mentoringapp.repository.MentorshipRepository;
import com.mentoringapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MentorshipServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private MentorshipRepository mentorshipRepository;

    @InjectMocks
    private MentorshipServiceImpl mentorshipService;

    private User mentor;
    private User mentee;

    @BeforeEach
    void setUp() {
        mentor = new User();
        mentor.setId(UUID.randomUUID());
        mentor.setEmail("mentor@example.com");
        mentor.setRole("MENTOR");

        mentee = new User();
        mentee.setId(UUID.randomUUID());
        mentee.setEmail("mentee@example.com");
        mentee.setRole("MENTEE");
    }

    @Test
    void assignMentorToMentee_shouldPersist_whenInputsAreValid() {
        // GIVEN
        when(userRepository.findById(mentor.getId())).thenReturn(Optional.of(mentor));
        when(userRepository.findByEmail(mentee.getEmail())).thenReturn(Optional.of(mentee));
        when(mentorshipRepository.findByMenteeId(mentee.getId())).thenReturn(Optional.empty());
        when(mentorshipRepository.save(any(Mentorship.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        Mentorship result = mentorshipService.assignMentorToMentee(mentor.getId(), mentee.getEmail());

        // THEN
        assertThat(result.getMentor()).isEqualTo(mentor);
        assertThat(result.getMentee()).isEqualTo(mentee);
        verify(mentorshipRepository).save(any(Mentorship.class));
    }

    @Test
    void assignMentorToMentee_shouldThrow_whenMentorNotFound() {
        // GIVEN
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // THEN
        assertThatThrownBy(() -> mentorshipService.assignMentorToMentee(UUID.randomUUID(), mentee.getEmail()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void assignMentorToMentee_shouldThrow_whenMenteeNotFound() {
        // GIVEN
        when(userRepository.findById(mentor.getId())).thenReturn(Optional.of(mentor));
        when(userRepository.findByEmail(mentee.getEmail())).thenReturn(Optional.empty());

        // THEN
        assertThatThrownBy(() -> mentorshipService.assignMentorToMentee(mentor.getId(), mentee.getEmail()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void assignMentorToMentee_shouldThrow_whenRolesAreIncorrect() {
        // GIVEN
        mentor.setRole("MENTEE"); // Wrong role
        when(userRepository.findById(mentor.getId())).thenReturn(Optional.of(mentor));
        when(userRepository.findByEmail(mentee.getEmail())).thenReturn(Optional.of(mentee));

        // THEN
        assertThatThrownBy(() -> mentorshipService.assignMentorToMentee(mentor.getId(), mentee.getEmail()))
                .isInstanceOf(InvalidMentorshipException.class);
    }

    @Test
    void assignMentorToMentee_shouldThrow_whenMenteeAlreadyLinked() {
        // GIVEN
        when(userRepository.findById(mentor.getId())).thenReturn(Optional.of(mentor));
        when(userRepository.findByEmail(mentee.getEmail())).thenReturn(Optional.of(mentee));
        when(mentorshipRepository.findByMenteeId(mentee.getId()))
                .thenReturn(Optional.of(new Mentorship()));

        // THEN
        assertThatThrownBy(() -> mentorshipService.assignMentorToMentee(mentor.getId(), mentee.getEmail()))
                .isInstanceOf(MentorshipAlreadyExistsException.class);
    }
} 