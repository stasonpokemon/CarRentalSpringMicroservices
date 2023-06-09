package com.carrental.microservices.userservice.service.impl;

import com.carrental.microservices.userservice.domain.dto.request.CreateUserRequestDTO;
import com.carrental.microservices.userservice.domain.dto.request.PassportRequestDTO;
import com.carrental.microservices.userservice.domain.dto.response.PassportResponseDTO;
import com.carrental.microservices.userservice.domain.dto.response.UserResponseDTO;
import com.carrental.microservices.userservice.domain.entity.Passport;
import com.carrental.microservices.userservice.domain.entity.Role;
import com.carrental.microservices.userservice.domain.entity.User;
import com.carrental.microservices.userservice.domain.mapper.PassportMapper;
import com.carrental.microservices.userservice.domain.mapper.UserMapper;
import com.carrental.microservices.userservice.exception.BadRequestException;
import com.carrental.microservices.userservice.exception.NotFoundException;
import com.carrental.microservices.userservice.repo.PassportRepository;
import com.carrental.microservices.userservice.repo.UserRepository;
import com.carrental.microservices.userservice.service.MailService;
import com.carrental.microservices.userservice.service.UserService;
import com.carrental.microservices.userservice.util.PassportUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation class for UserService.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PassportRepository passportRepository;

    private final MailService mailService;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final PassportMapper passportMapper = Mappers.getMapper(PassportMapper.class);


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDTO> findUser(UUID userId) {

        log.info("Finding user by id: {}", userId);

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(findUserByIdOrThrowException(userId));
        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(userResponseDTO, HttpStatus.OK);

        log.info("Find user: {} by id: {}", userResponseDTO, userId);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable) {

        log.info("Finding all users");

        Page<UserResponseDTO> userResponseDTOPage = new PageImpl<>(userRepository.findAll(pageable)
                .stream()
                .map(userMapper::userToUserResponseDTO)
                .collect(Collectors.toList()));

        log.info("Find all users: {}", userResponseDTOPage.getContent());

        return new ResponseEntity<>(userResponseDTOPage, HttpStatus.OK);
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PassportResponseDTO> findPassportByUserId(UUID userId) {

        log.info("Finding passport by userId: {}", userId);

        Passport passport = findUserByIdOrThrowException(userId).getPassport();

        if (passport == null) {
            throw new NotFoundException(String.format("Passport not found for user with id = %s", userId));
        }

        log.info("Find passport: {} by userId: {}", passport, userId);

        return new ResponseEntity<>(passportMapper.passportToPassportResponseDTO(passport), HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<PassportResponseDTO> createPassportForUser(UUID userId,
                                                                     PassportRequestDTO passportRequestDTO) {

        log.info("Creating new passport: {} for user with id: {}", passportRequestDTO, userId);

        User user = findUserByIdOrThrowException(userId);

        if (user.getPassport() != null) {
            throw new BadRequestException(String.format("User with id = %s already has passport", userId));
        }

        Passport passport = passportMapper.passportDTOtoPassport(passportRequestDTO);
        passport.setUser(user);
        passport = passportRepository.save(passport);

        log.info("Creat new passport: {} for user with id: {}", passport, userId);

        return new ResponseEntity<>(
                passportMapper.passportToPassportResponseDTO(passport), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<PassportResponseDTO> updateUsersPassport(UUID userId,
                                                                   PassportRequestDTO passportRequestDTO) {

        log.info("Updating user's passport: {} by userId: {}", passportRequestDTO, userId);

        Passport passport = findPassportByUserOrThrowException(findUserByIdOrThrowException(userId));
        PassportUtil.getInstance().copyNotNullFieldsFromPassportDTOToPassport(passportRequestDTO, passport);

        log.info("Update user's passport: {} by userId: {}", passport, userId);

        return new ResponseEntity<>(passportMapper.passportToPassportResponseDTO(passport), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponseDTO> registrationNewUser(CreateUserRequestDTO createUserRequestDTO) {

        log.info("trying to register a new user: {}", createUserRequestDTO);

        if (userRepository.findUserByUsername(createUserRequestDTO.getUsername()).isPresent()) {

            log.info("There is user with username: {}", createUserRequestDTO.getUsername());

            throw new BadRequestException("There is user with the same username");
        }

        if (userRepository.findUserByEmail(createUserRequestDTO.getEmail()).isPresent()) {

            log.info("There is user with email: {}", createUserRequestDTO.getEmail());

            throw new BadRequestException("There is user with the same email");
        }

        User user = createUser(createUserRequestDTO);

        log.info("Save registered user: {}", user);

        mailService.sendActivationCode(user);

        log.info("Sent message with activation code");

        return new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);
    }

    @Override
    @Transactional
    public User createUser(CreateUserRequestDTO createUserRequestDTO) {
        User user = userMapper.createUserRequestDTOToUser(createUserRequestDTO);
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDTO> activateUser(String activateCode) {

        log.info("Trying to activate user");

        User user = userRepository.findUserByActivationCode(activateCode)
                .orElseThrow(() -> new NotFoundException("Activation code is note found"));
        user.setActivationCode(null);
        user.setActive(true);

        log.info("Activate user: {}", user);

        return new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<UserResponseDTO> blockUser(UUID id) {

        log.info("Blocking user with id: {}", id);

        User user = findUserByIdOrThrowException(id);

        if (!user.isActive()) {
            throw new BadRequestException(String.format("User with id: %s is already blocked", id));
        }

        user.setActive(false);

        log.info("Block user: {}", user);

        return new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDTO> unlockUser(UUID id) {

        log.info("Unlocking user with id: {}", id);

        User user = findUserByIdOrThrowException(id);

        if (user.isActive()) {
            throw new BadRequestException(String.format("User with id: %s is already unlocked", id));
        }

        user.setActive(true);

        log.info("Unlock user: {}", user);


        return new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByIdOrThrowException(UUID userId) {

        log.info("Finding user by id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                User.class, userId));

        log.info("Find user: {}", user);

        return user;
    }

    @Transactional(readOnly = true)
    public Passport findPassportByUserOrThrowException(User user) {

        log.info("Finding passport by user: {}", user);

        Passport passport = Optional.ofNullable(user.getPassport())
                .orElseThrow(() -> new BadRequestException(
                        String.format("Passport not found for user with id = %s", user.getId())));

        log.info("Finding passport: {} by user: {}", passport, user);

        return passport;
    }
}
