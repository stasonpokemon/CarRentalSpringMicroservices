package com.carrental.microservices.orderservice.service.impl;

import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;
import com.carrental.microservices.orderservice.exception.BadRequestException;
import com.carrental.microservices.orderservice.exception.NotFoundException;
import com.carrental.microservices.orderservice.feign.CarServiceFeignClient;
import com.carrental.microservices.orderservice.service.CarService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation class for CarService.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarServiceFeignClient carServiceFeignClient;

    @Override
    public CarResponseDTO updateCarStatusAsFree(UUID carId) {
        CarResponseDTO updatedCar;

        try {

            log.info("Trying to set car's status as free");

            updatedCar = carServiceFeignClient.updateCarStatusAsFree(carId);

            log.info("Updated car: {}", updatedCar);

        } catch (FeignException.NotFound e) {
            throw new NotFoundException(e.getMessage());
        } catch (FeignException.BadRequest e) {
            throw new BadRequestException(e.getMessage());
        }

        return updatedCar;
    }

    @Override
    public CarResponseDTO updateCarStatusAsBroken(UUID carId, String damageDescription) {
        CarResponseDTO updatedCar;

        try {
            log.info("Trying to set car's status as broken");

            updatedCar = carServiceFeignClient.setCarAsBroken(carId, damageDescription);

            log.info("Updated car: {}", updatedCar);

        } catch (FeignException.NotFound e) {
            throw new NotFoundException(e.getMessage());
        } catch (FeignException.BadRequest e) {
            throw new BadRequestException(e.getMessage());
        }

        return updatedCar;
    }

    @Override
    public CarResponseDTO updateCarStatusAsBusy(UUID carId) {
        CarResponseDTO updatedCar;

        try {
            log.info("Trying to set car's status as busy");

            updatedCar = carServiceFeignClient.updateCarStatusAsBusy(carId);

            log.info("Updated car: {}", updatedCar);

        } catch (FeignException.NotFound e) {
            throw new NotFoundException(e.getMessage());
        } catch (FeignException.BadRequest e) {
            throw new BadRequestException(e.getMessage());
        }

        return updatedCar;
    }

    @Override
    public CarResponseDTO findCarById(UUID carId) {

        log.info("Trying to find car with id: {}", carId);

        CarResponseDTO car;

        try {
            car = carServiceFeignClient.findCarByIdNotMarkedAsDeleted(carId);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException(e.getMessage());
        }

        return car;
    }

    @Override
    public CarResponseDTO findRepairedAndFreeCarById(UUID carId) {
        CarResponseDTO car = findCarById(carId);

        if (car.isBroken()) {
            throw new BadRequestException(
                    String.format("The car with id = %s is broken", carId));
        }

        if (car.isBusy()) {
            throw new BadRequestException(
                    String.format("The car with id = %s isn't free", carId));
        }

        return car;
    }
}
