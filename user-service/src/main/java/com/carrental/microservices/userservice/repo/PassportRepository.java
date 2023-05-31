package com.carrental.microservices.userservice.repo;

import com.carrental.microservices.userservice.domain.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * JpaRepository, which works with Passport entity.
 */
@Repository
public interface PassportRepository extends JpaRepository<Passport, UUID> {

}
