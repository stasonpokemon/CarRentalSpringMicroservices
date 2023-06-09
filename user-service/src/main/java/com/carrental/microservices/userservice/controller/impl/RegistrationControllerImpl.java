package com.carrental.microservices.userservice.controller.impl;

import com.carrental.microservices.userservice.controller.RegistrationController;
import com.carrental.microservices.userservice.domain.dto.request.CreateUserRequestDTO;
import com.carrental.microservices.userservice.domain.dto.response.UserResponseDTO;
import com.carrental.microservices.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for RegistrationController.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class RegistrationControllerImpl implements RegistrationController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDTO> saveRegisteredUser(CreateUserRequestDTO createUserRequestDTO) {

        log.info("POST request to save registered user: {}", createUserRequestDTO);

        return userService.registrationNewUser(createUserRequestDTO);
    }

    @Override
    public ResponseEntity<UserResponseDTO> activateUser(String activateCode) {

        log.info("GET request to activate user");

        return userService.activateUser(activateCode);
    }
}
