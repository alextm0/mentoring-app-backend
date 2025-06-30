package com.mentoringapp.mapper;

import com.mentoringapp.domain.Mentorship;
import com.mentoringapp.dto.response.MentorshipResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MentorshipMapper {
    MentorshipResponseDTO mentorshipToMentorshipResponseDTO(Mentorship mentorship);
    List<MentorshipResponseDTO> mentorshipsToMentorshipResponseDTOs(List<Mentorship> mentorships);
} 