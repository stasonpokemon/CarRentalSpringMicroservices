package com.carrental.microservices.carservice.service.impl;


import com.carrental.microservices.carservice.domain.entity.Car;
import com.carrental.microservices.carservice.domain.dto.request.CreateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.request.UpdateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.response.CarResponseDTO;
import com.carrental.microservices.carservice.domain.mapper.CarMapper;
import com.carrental.microservices.carservice.exception.BadRequestException;
import com.carrental.microservices.carservice.exception.NotFoundException;
import com.carrental.microservices.carservice.repo.CarRepository;
import com.carrental.microservices.carservice.service.CarService;
import com.carrental.microservices.carservice.util.CarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation class for CarService.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CarResponseDTO> findById(UUID id, Boolean withMarkedAsDeleted) {

        log.info("Finding car with id: {}", id);

        CarResponseDTO carResponseDTO;

        // todo - add role validation if withMarkedAsDeleted is true, access only for admin
        if (withMarkedAsDeleted) {
            carResponseDTO = carMapper.carToCarResponseDTO(findCarByIdOrThrowException(id));
        } else {
            carResponseDTO = carMapper.carToCarResponseDTO(carRepository.findByIdAndDeleted(id, false)
                    .orElseThrow(() -> new NotFoundException(Car.class, id)));
        }

        ResponseEntity<CarResponseDTO> response = new ResponseEntity<>(carResponseDTO, HttpStatus.OK);

        log.info("Find car: {}", carResponseDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<CarResponseDTO>> findAll(Pageable pageable, Boolean withMarkedAsDeleted) {

        log.info("Finding all cars");

        Page<Car> cars;

        // todo - add role validation if withMarkedAsDeleted is true, access only for admin
        if (withMarkedAsDeleted) {
            cars = carRepository.findAll(pageable);
        } else {
            cars = carRepository.findAllByDeleted(false, pageable);
        }

        Page<CarResponseDTO> carResponseDTOPage = new PageImpl<>(cars
                .stream()
                .map(carMapper::carToCarResponseDTO)
                .collect(Collectors.toList()));

        ResponseEntity<Page<CarResponseDTO>> response = new ResponseEntity<>(carResponseDTOPage, HttpStatus.OK);

        log.info("Find all cars: {}", carResponseDTOPage.getContent());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<CarResponseDTO>> findAllFreeNotMarkAsDeleted(Pageable pageable) {

        log.info("Finding all free not mark as deleted cars");

        Page<CarResponseDTO> carResponseDTOPage =
                new PageImpl<>(carRepository.findAllByBusyAndDeleted(false, false, pageable)
                        .stream()
                        .map(carMapper::carToCarResponseDTO)
                        .collect(Collectors.toList()));

        if (carResponseDTOPage.isEmpty()) {
            throw new NotFoundException("There isn't free cars not mark as deleted");
        }

        ResponseEntity<Page<CarResponseDTO>> response = new ResponseEntity<>(carResponseDTOPage, HttpStatus.OK);

        log.info("Find all free not mark as deleted cars: {}", carResponseDTOPage.getContent());

        return response;
    }

    @Override
    public ResponseEntity<CarResponseDTO> save(CreateCarRequestDTO createCarRequestDTO) {

        log.info("Saving car: {}", createCarRequestDTO);

        Car car = carMapper.createCarRequestDTOToCar(createCarRequestDTO);
        car.setDamageStatus("Without damage");
        car.setBusy(false);
        car.setDeleted(false);
        Car savedCar = carRepository.save(car);

        ResponseEntity<CarResponseDTO> response =
                new ResponseEntity<>(carMapper.carToCarResponseDTO(savedCar), HttpStatus.OK);

        log.info("Save car: {}", car);

        return response;
    }

    @Override
    public ResponseEntity<CarResponseDTO> update(UUID id,
                                                 UpdateCarRequestDTO updateCarRequestDTO) {

        log.info("Updating car: {} with id: {}", updateCarRequestDTO, id);

        Car car = findCarByIdOrThrowException(id);
        CarUtil.getInstance().copyNotNullFieldsFromUpdateCarDTOToCar(updateCarRequestDTO, car);

        ResponseEntity<CarResponseDTO> response =
                new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Update car: {} with id: {}", car, id);

        return response;
    }

    @Override
    public ResponseEntity<CarResponseDTO> fixBrokenCar(UUID carId) {

        log.info("Fixing broken car with id: {}", carId);

        Car car = findCarByIdOrThrowException(carId);

        if (car.isDeleted()) {
            throw new BadRequestException(String.format("Unable to fix car. Car with id = %s is deleted", carId));
        }

        if (!car.isBroken()) {
            throw new BadRequestException(String.format("Unable to fix car. Car with id = %s already fixed", carId));
        }

        car.setBroken(false);
        car.setDamageStatus("Without damage");
        car.setBusy(false);

        ResponseEntity<CarResponseDTO> response =
                new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Fix broken car: {} with id: {}", car, carId);

        return response;

    }

    @Override
    public ResponseEntity<CarResponseDTO> setCarAsBroken(UUID carId,
                                                         String damageStatus) {

        log.info("Setting the car as broken with id: {} and damage description: {}", carId, damageStatus);

        Car car = findCarByIdOrThrowException(carId);

        if (car.isBroken()) {
            throw new BadRequestException(String.format("Car with id = %s is already broken", carId));
        }

        if (car.isDeleted()) {
            throw new BadRequestException(String.format("Car with id = %s is deleted", carId));
        }

        car.setBroken(true);
        car.setBusy(true);
        car.setDamageStatus(damageStatus);

        ResponseEntity<CarResponseDTO> response =
                new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Set the car: {} as broken with id: {}", car, carId);
        return response;
    }

    @Override
    public ResponseEntity<CarResponseDTO> setCarAsBusy(UUID carId) {

        log.info("Setting the car as busy with id: {}", carId);

        Car car = carRepository.findByIdAndDeleted(carId, false)
                .orElseThrow(() -> new NotFoundException(Car.class, carId));

        log.info("Found car: {}", car);

        if (car.isBroken()) {
            throw new BadRequestException(String.format("Car with id = %s is broken", carId));
        }

        if (car.isBusy()) {
            throw new BadRequestException(String.format("Car with id = %s is busy now", carId));
        }

        car.setBusy(true);

        ResponseEntity<CarResponseDTO> response =
                new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Set the car: {} as busy", car);

        return response;
    }

    @Override
    public ResponseEntity<CarResponseDTO> setCarAsFree(UUID carId) {

        log.info("Setting the car as free with id: {}", carId);

        Car car = findCarByIdOrThrowException(carId);

        log.info("Found car: {}", car);

        if (car.isBroken()) {
            throw new BadRequestException(String.format("Car with id = %s is broken", carId));
        }

        if (!car.isBusy()) {
            throw new BadRequestException(String.format("Car with id = %s is free now", carId));
        }

        car.setBusy(false);

        ResponseEntity<CarResponseDTO> response =
                new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Set the car: {} as free", car);

        return response;
    }


    @Override
    public ResponseEntity<CarResponseDTO> markCarAsDeleted(UUID id) {

        log.info("Marking car with id: {} as deleted", id);

        Car car = findCarByIdOrThrowException(id);

        if (car.isDeleted()) {
            throw new BadRequestException(String.format("Car with id = %s already marked as deleted", id));
        }

        car.setDeleted(true);
        car.setBusy(true);

        ResponseEntity<CarResponseDTO> response =
                new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Mark car: {} with id: {} as deleted", car, id);

        return response;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public Car findCarByIdOrThrowException(UUID id) {

        log.info("Finding car with id: {}", id);

        Car car = carRepository.findById(id).orElseThrow(() -> new NotFoundException(
                Car.class, id));

        log.info("Find car: {} with id: {}", car, id);

        return car;
    }


}
