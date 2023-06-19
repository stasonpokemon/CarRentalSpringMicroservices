package com.carrental.microservices.orderservice.service.impl;

import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;
import com.carrental.microservices.orderservice.exception.BadRequestException;
import com.carrental.microservices.orderservice.exception.NotFoundException;
import com.carrental.microservices.orderservice.feign.CarServiceFeignClient;
import com.carrental.microservices.orderservice.kafka.messages.CarStatus;
import com.carrental.microservices.orderservice.kafka.messages.CarStatusMessage;
import com.carrental.microservices.orderservice.kafka.producer.CarStatusKafkaProducer;
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
public class CarServiceImpl implements CarService {

    private final CarStatusKafkaProducer carStatusKafkaProducer;

    private final CarServiceFeignClient carServiceFeignClient;

    @Override
    public void updateCarStatusAsFree(UUID carId) {

        log.info("Trying to set car's status as free for car with id: {}", carId);

        carStatusKafkaProducer.sendMessage(
                CarStatusMessage.builder()
                        .carId(carId)
                        .carStatus(CarStatus.FREE).build());
    }

    @Override
    @Transactional
    public void updateCarStatusAsBroken(UUID carId, String damageDescription) {

        log.info("Trying to set car's status as broken for car with id: {}", carId);

        carStatusKafkaProducer.sendMessage(
                CarStatusMessage.builder()
                        .carId(carId)
                        .carStatus(CarStatus.BROKEN)
                        .damageDescription(damageDescription).build());
    }

    @Override
    public void updateCarStatusAsBusy(UUID carId) {

        log.info("Trying to set car's status as busy for car with id: {}", carId);

        carStatusKafkaProducer.sendMessage(
                CarStatusMessage.builder()
                        .carId(carId)
                        .carStatus(CarStatus.BUSY).build());
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDTO findCarById(UUID carId) {

        log.info("Trying to find car by id: {}", carId);

        CarResponseDTO car;

        try {
            car = carServiceFeignClient.findCarByIdNotMarkedAsDeleted(carId);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException(e.getMessage());
        }

        log.info("Found car: {}", car);

        return car;
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDTO findFreeCarById(UUID carId) {

        log.info("Trying to find repaired and free car by id: {}", carId);

        CarResponseDTO car = findCarById(carId);

        if (car.getCarStatus().equals(CarStatus.BROKEN)) {
            throw new BadRequestException(
                    String.format("The car with id = %s is broken", carId));
        }

        if (car.getCarStatus().equals(CarStatus.BUSY)) {
            throw new BadRequestException(
                    String.format("The car with id = %s isn't free", carId));
        }

        log.info("Found car: {}", car);

        return car;
    }
}
