package com.carrental.microservices.carservice.repo;

import com.carrental.microservices.carservice.domain.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JpaRepository, which works with Car entity.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    Page<Car> findAllByDeleted(boolean isDeleted, Pageable pageable);

    List<Car> findAllByBusyAndDeleted(boolean isBusy, boolean isDeleted, Pageable pageable);

    Optional<Car> findByIdAndDeleted(UUID id, boolean isDeleted);

}
