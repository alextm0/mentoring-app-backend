package com.mentoringapp.controller;

import com.mentoringapp.domain.User;
import com.mentoringapp.dto.request.CreateUserRequestDTO;
import com.mentoringapp.dto.response.CreateUserResponseDTO;
import com.mentoringapp.dto.response.GetUserResponseDTO;
import com.mentoringapp.mapper.UserMapper;
import com.mentoringapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Operations related to users, mentors, and mentees")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    @GetMapping
    public ResponseEntity<List<GetUserResponseDTO>> getAllUsers() {
        List<User> users = userService.getUsers();
        List<GetUserResponseDTO> userDTOs = userMapper.usersToGetUserResponseDTOs(users);
        return ResponseEntity.ok(userDTOs);
    }

    @Operation(summary = "Get users by role", description = "Retrieve users filtered by their role (MENTOR or MENTEE)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users by role"),
        @ApiResponse(responseCode = "400", description = "Invalid role provided")
    })
    @GetMapping("/role/{role}")
    public ResponseEntity<List<GetUserResponseDTO>> getUsersByRole(
            @Parameter(description = "Role to filter by (MENTOR or MENTEE)", example = "MENTOR")
            @PathVariable String role) {
        List<User> users = userService.getUsersByRole(role.toUpperCase());
        List<GetUserResponseDTO> userDTOs = userMapper.usersToGetUserResponseDTOs(users);
        return ResponseEntity.ok(userDTOs);
    }

    @Operation(summary = "Get all mentors", description = "Retrieve a list of all mentors")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved mentors")
    @GetMapping("/mentors")
    public ResponseEntity<List<GetUserResponseDTO>> getMentors() {
        List<User> mentors = userService.getUsersByRole("MENTOR");
        List<GetUserResponseDTO> mentorDTOs = userMapper.usersToGetUserResponseDTOs(mentors);
        return ResponseEntity.ok(mentorDTOs);
    }

    @Operation(summary = "Get all mentees", description = "Retrieve a list of all mentees")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved mentees")
    @GetMapping("/mentees")
    public ResponseEntity<List<GetUserResponseDTO>> getMentees() {
        List<User> mentees = userService.getUsersByRole("MENTEE");
        List<GetUserResponseDTO> menteeDTOs = userMapper.usersToGetUserResponseDTOs(mentees);
        return ResponseEntity.ok(menteeDTOs);
    }

    @Operation(summary = "Get mentees for a specific mentor", description = "Retrieve all mentees assigned to a specific mentor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved mentees for the mentor"),
        @ApiResponse(responseCode = "404", description = "Mentor not found")
    })
    @GetMapping("/mentors/{mentorId}/mentees")
    public ResponseEntity<List<GetUserResponseDTO>> getMenteesForMentor(
            @Parameter(description = "ID of the mentor")
            @PathVariable UUID mentorId) {
        List<User> mentees = userService.getMentees(mentorId);
        List<GetUserResponseDTO> menteeDTOs = userMapper.usersToGetUserResponseDTOs(mentees);
        return ResponseEntity.ok(menteeDTOs);
    }

    @Operation(summary = "Get mentor for a specific mentee", description = "Retrieve the mentor assigned to a specific mentee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved mentor for the mentee"),
        @ApiResponse(responseCode = "404", description = "Mentee not found or has no mentor")
    })
    @GetMapping("/mentees/{menteeId}/mentor")
    public ResponseEntity<GetUserResponseDTO> getMentorForMentee(
            @Parameter(description = "ID of the mentee")
            @PathVariable UUID menteeId) {
        User mentor = userService.getMentor(menteeId);
        GetUserResponseDTO mentorDTO = userMapper.userToGetUserResponseDTO(mentor);
        return ResponseEntity.ok(mentorDTO);
    }

    @Operation(summary = "Create a new user", description = "Create a new user (mentor or mentee)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
        @ApiResponse(responseCode = "409", description = "User with email already exists")
    })
    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(
            @Valid @RequestBody CreateUserRequestDTO requestDTO) {
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setRole(requestDTO.getRole().toUpperCase());
        
        User createdUser = userService.createUser(user);
        CreateUserResponseDTO responseDTO = userMapper.userToCreateUserResponseDTO(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
