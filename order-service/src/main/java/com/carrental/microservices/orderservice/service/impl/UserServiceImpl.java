package com.carrental.microservices.orderservice.service.impl;

import com.carrental.microservices.orderservice.domain.dto.response.UserResponseDTO;
import com.carrental.microservices.orderservice.exception.BadRequestException;
import com.carrental.microservices.orderservice.exception.NotFoundException;
import com.carrental.microservices.orderservice.feign.UserServiceFeignClient;
import com.carrental.microservices.orderservice.service.UserService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


/**
 * Implementation class for UserService.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public UserResponseDTO findUserById(UUID userId) {

        log.info("Trying to find a user with id: {}", userId);

        UserResponseDTO user;

        try {
            user = userServiceFeignClient.findUserById(userId);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Not Found user with id " + userId);
        }

        log.info("Found user: {}", user);

        return user;
    }

    @Override
    public UserResponseDTO findUserWithPassportById(UUID userId) {

        log.info("Trying to find user with passport by id: {}", userId);

        UserResponseDTO user = findUserById(userId);

        if (user.getPassportId() == null) {
            throw new BadRequestException("The user: %s must have a passport to create a new order".formatted(user));
        }

        log.info("Found user: {}", user);

        return user;
    }
}
