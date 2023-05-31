package com.carrental.microservices.userservice.repo;

import com.carrental.microservices.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JpaRepository, which works with User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(String username);

    Optional<User>  findUserByEmail(String email);

    Optional<User>  findUserByActivationCode(String activationCode);
}
