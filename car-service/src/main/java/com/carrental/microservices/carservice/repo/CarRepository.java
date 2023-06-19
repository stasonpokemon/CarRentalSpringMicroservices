package com.carrental.microservices.carservice.repo;

import com.carrental.microservices.carservice.domain.entity.Car;
import com.carrental.microservices.carservice.domain.entity.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JpaRepository, which works with Car entity.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    Page<Car> findAllByCarStatusIsNot(CarStatus carStatus, Pageable pageable);

    Page<Car> findAllByCarStatus(CarStatus carStatus, Pageable pageable);

    Optional<Car> findByIdAndCarStatusIsNot(UUID id, CarStatus carStatus);
}
