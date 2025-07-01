package com.mentoringapp.service.impl;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.domain.User;
import com.mentoringapp.domain.UserRole;
import com.mentoringapp.exceptions.UserException;
import com.mentoringapp.exceptions.UserNotFoundException;
import com.mentoringapp.repository.MentorshipRepository;
import com.mentoringapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MentorshipRepository mentorshipRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User mentor;
    private User mentee;
    private Mentorship mentorship;

    @BeforeEach
    void setUp() {
        mentor = new User();
        mentor.setId(UUID.randomUUID());
        mentor.setEmail("mentor@example.com");
        mentor.setRole(UserRole.MENTOR);

        mentee = new User();
        mentee.setId(UUID.randomUUID());
        mentee.setEmail("mentee@example.com");
        mentee.setRole(UserRole.MENTEE);

        mentorship = new Mentorship();
        mentorship.setId(UUID.randomUUID());
        mentorship.setMentor(mentor);
        mentorship.setMentee(mentee);
    }

    @Test
    void createUser_shouldPersistAndReturnUser_whenEmailIsUnique() {
        // GIVEN
        when(userRepository.findByEmail("mentor@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        User created = userService.createUser(mentor);

        // THEN
        assertThat(created).isEqualTo(mentor);
        verify(userRepository).save(mentor);
    }

    @Test
    void createUser_shouldThrowException_whenEmailExists() {
        // GIVEN
        when(userRepository.findByEmail("mentor@example.com")).thenReturn(Optional.of(mentor));

        // THEN
        assertThatThrownBy(() -> userService.createUser(mentor))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("already exists");

        verify(userRepository, never()).save(any());
    }

    @Test
    void getMentor_shouldReturnMentor_whenActiveMentorshipExists() {
        // GIVEN
        when(mentorshipRepository.findByMenteeId(mentee.getId()))
                .thenReturn(Optional.of(mentorship));

        // WHEN
        User result = userService.getMentor(mentee.getId());

        // THEN
        assertThat(result).isEqualTo(mentor);
    }

    @Test
    void getMentor_shouldThrow_whenMentorshipNotFound() {
        // GIVEN
        when(mentorshipRepository.findByMenteeId(mentee.getId()))
                .thenReturn(Optional.empty());

        // THEN
        assertThatThrownBy(() -> userService.getMentor(mentee.getId()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getMentees_shouldReturnList_whenMentorExists() {
        // GIVEN
        when(userRepository.findById(mentor.getId())).thenReturn(Optional.of(mentor));
        when(mentorshipRepository.findByMentorId(mentor.getId()))
                .thenReturn(List.of(mentorship));

        // WHEN
        List<User> mentees = userService.getMentees(mentor.getId());

        // THEN
        assertThat(mentees).containsExactly(mentee);
    }
} 