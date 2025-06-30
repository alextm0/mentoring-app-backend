package com.mentoringapp.controller;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.dto.request.AssignMentorRequestDTO;
import com.mentoringapp.dto.response.MentorshipResponseDTO;
import com.mentoringapp.mapper.MentorshipMapper;
import com.mentoringapp.service.MentorshipService;
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
@RequestMapping("/api/v1/mentorships")
@RequiredArgsConstructor
@Tag(name = "Mentorship Management", description = "Operations for managing mentor-mentee relationships")
public class MentorshipController {
    private final MentorshipService mentorshipService;
    private final MentorshipMapper mentorshipMapper;

    @Operation(summary = "Get all mentorships", description = "Retrieve a list of all active mentorships in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved mentorships")
    @GetMapping
    public ResponseEntity<List<MentorshipResponseDTO>> getAllMentorships() {
        List<Mentorship> mentorships = mentorshipService.getAllMentorships();
        List<MentorshipResponseDTO> dtos = mentorshipMapper.mentorshipsToMentorshipResponseDTOs(mentorships);
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Assign a mentor to a mentee", description = "Create a new mentorship by assigning a mentor to a mentee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Mentorship created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Mentor or mentee not found"),
        @ApiResponse(responseCode = "409", description = "Mentee already has a mentor")
    })
    @PostMapping
    public ResponseEntity<MentorshipResponseDTO> assignMentorToMentee(
            @Valid @RequestBody AssignMentorRequestDTO requestDTO) {
        Mentorship mentorship = mentorshipService.assignMentorToMentee(requestDTO.getMentorId(), requestDTO.getMenteeEmail());
        MentorshipResponseDTO dto = mentorshipMapper.mentorshipToMentorshipResponseDTO(mentorship);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Remove a mentorship", description = "Delete an existing mentorship relationship")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Mentorship removed successfully"),
        @ApiResponse(responseCode = "404", description = "Mentorship not found")
    })
    @DeleteMapping("/{mentorshipId}")
    public ResponseEntity<Void> removeMentorship(
            @Parameter(description = "ID of the mentorship to remove", example = "")
            @PathVariable UUID mentorshipId) {
        mentorshipService.removeMentorship(mentorshipId);
        return ResponseEntity.noContent().build();
    }
} 