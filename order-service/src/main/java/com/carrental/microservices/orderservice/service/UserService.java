package com.carrental.microservices.orderservice.service;

import com.carrental.microservices.orderservice.domain.dto.response.UserResponseDTO;

import java.util.UUID;

/**
 * The UserService interface, which stores the business logic for working with an user.
 */
public interface UserService {

    UserResponseDTO findUserById(UUID userId);

    UserResponseDTO findUserWithPassportById(UUID userId);
}
