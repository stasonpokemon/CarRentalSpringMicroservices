package com.carrental.microservices.userservice.domain.mapper;

import com.carrental.microservices.userservice.domain.dto.response.UserResponseDTO;
import com.carrental.microservices.userservice.domain.entity.User;
import com.carrental.microservices.userservice.domain.dto.request.CreateUserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface presents the basic contract for converting User to UserDTO and vice versa.
 */
@Mapper
public interface UserMapper {

    @Mapping(target = "passportId", source = "passport.id")
    UserResponseDTO userToUserResponseDTO(User user);

    User createUserRequestDTOToUser(CreateUserRequestDTO createUserRequestDTO);

    @Mapping(target = "passport.id", source = "passportId")
    User userRequestDTOToUser(UserResponseDTO userResponseDTO);
}
