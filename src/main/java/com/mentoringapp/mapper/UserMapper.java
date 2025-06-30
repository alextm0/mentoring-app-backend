package com.mentoringapp.mapper;

import com.mentoringapp.domain.User;
import com.mentoringapp.dto.response.CreateUserResponseDTO;
import com.mentoringapp.dto.response.GetUserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    // For user creation
    CreateUserResponseDTO userToCreateUserResponseDTO(User user);
    
    // For user retrieval
    GetUserResponseDTO userToGetUserResponseDTO(User user);
    List<GetUserResponseDTO> usersToGetUserResponseDTOs(List<User> users);
}
