package com.mentoringapp.mapper;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.dto.response.CreateMentorshipResponseDTO;
import com.mentoringapp.dto.response.GetMentorshipResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface MentorshipMapper {
    // For mentorship creation
    CreateMentorshipResponseDTO mentorshipToCreateMentorshipResponseDTO(Mentorship mentorship);
    
    // For mentorship retrieval
    GetMentorshipResponseDTO mentorshipToGetMentorshipResponseDTO(Mentorship mentorship);
    List<GetMentorshipResponseDTO> mentorshipsToGetMentorshipResponseDTOs(List<Mentorship> mentorships);
} 