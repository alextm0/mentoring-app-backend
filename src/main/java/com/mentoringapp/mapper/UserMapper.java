package com.mentoringapp.mapper;

import com.mentoringapp.domain.User;
import com.mentoringapp.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO userToUserResponseDTO(User user);
    List<UserResponseDTO> usersToUserResponseDTOs(List<User> users);
}
