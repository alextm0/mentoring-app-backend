package com.mentoringapp.mapper;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.dto.response.CreateMentorshipResponseDTO;
import com.mentoringapp.dto.response.GetMentorshipResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MentorshipMapper {
    // For mentorship creation
    CreateMentorshipResponseDTO mentorshipToCreateMentorshipResponseDTO(Mentorship mentorship);
    
    // For mentorship retrieval
    GetMentorshipResponseDTO mentorshipToGetMentorshipResponseDTO(Mentorship mentorship);
    List<GetMentorshipResponseDTO> mentorshipsToGetMentorshipResponseDTOs(List<Mentorship> mentorships);
} 