package com.carrental.microservices.orderservice.service;

import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;

import java.util.UUID;

/**
 * The CarService interface, which stores the business logic for working with a car.
 */
public interface CarService {

    void updateCarStatusAsFree(UUID carId);

    void updateCarStatusAsBroken(UUID carId, String damageDescription);

    void updateCarStatusAsBusy(UUID carId);

    CarResponseDTO findCarById(UUID carId);

    CarResponseDTO findFreeCarById(UUID carId);
}
