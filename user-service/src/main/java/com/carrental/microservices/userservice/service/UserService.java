package com.carrental.microservices.userservice.service;

import com.carrental.microservices.userservice.domain.entity.User;
import com.carrental.microservices.userservice.domain.dto.request.CreateUserRequestDTO;
import com.carrental.microservices.userservice.domain.dto.request.PassportRequestDTO;
import com.carrental.microservices.userservice.domain.dto.response.PassportResponseDTO;
import com.carrental.microservices.userservice.domain.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The UserService interface, which stores the business logic for working with an user.
 */
public interface UserService {

    ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable);

    ResponseEntity<UserResponseDTO> blockUser(UUID userId);

    ResponseEntity<UserResponseDTO> unlockUser(UUID userId);

    ResponseEntity<PassportResponseDTO> findPassportByUserId(UUID userId);

    ResponseEntity<PassportResponseDTO> createPassportForUser(UUID userId,
                                            PassportRequestDTO passportRequestDTO);

    ResponseEntity<PassportResponseDTO> updateUsersPassport(UUID userId,
                                          PassportRequestDTO passportRequestDTO);

    ResponseEntity<UserResponseDTO> registrationNewUser(CreateUserRequestDTO createUserRequestDTO);

    @Transactional
    User createUser(CreateUserRequestDTO createUserRequestDTO);

    ResponseEntity<UserResponseDTO> activateUser(String activateCode);

    ResponseEntity<UserResponseDTO> findUser(UUID userId);

    User findUserByIdOrThrowException(UUID userId);


}
