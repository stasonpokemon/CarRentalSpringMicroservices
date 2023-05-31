package com.carrental.microservices.carservice.service;


import com.carrental.microservices.carservice.domain.entity.Car;
import com.carrental.microservices.carservice.domain.dto.request.CreateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.request.UpdateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.response.CarResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * The CarService interface, which stores the business logic for working with an car.
 */
public interface CarService {


    ResponseEntity<CarResponseDTO> findById(UUID carId, Boolean withMarkedAsDeleted);

    ResponseEntity<Page<CarResponseDTO>> findAll(Pageable pageable, Boolean withMarkedAsDeleted);

    ResponseEntity<Page<CarResponseDTO>> findAllFreeNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<CarResponseDTO> save(CreateCarRequestDTO createCarRequestDTO);

    ResponseEntity<CarResponseDTO> update(UUID carId,
                             UpdateCarRequestDTO updateCarRequestDTO);

    ResponseEntity<CarResponseDTO> markCarAsDeleted(UUID carId);

    Car findCarByIdOrThrowException(UUID carId);

    ResponseEntity<CarResponseDTO> fixBrokenCar(UUID carId);

    ResponseEntity<CarResponseDTO> setCarAsBroken(UUID carId,
                                     String damageStatus);

    ResponseEntity<CarResponseDTO> setCarAsBusy(UUID carId);

    ResponseEntity<CarResponseDTO> setCarAsFree(UUID carId);

}
