package com.carrental.microservices.carservice.service.impl;


import com.carrental.microservices.carservice.domain.dto.request.CreateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.request.UpdateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.response.CarResponseDTO;
import com.carrental.microservices.carservice.domain.entity.Car;
import com.carrental.microservices.carservice.domain.entity.CarStatus;
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
            carResponseDTO = carMapper
                    .carToCarResponseDTO(carRepository.findByIdAndCarStatusIsNot(id, CarStatus.DELETED)
                    .orElseThrow(() -> new NotFoundException(Car.class, id)));
        }

        log.info("Find car: {}", carResponseDTO);

        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<CarResponseDTO>> findAllCars(Pageable pageable, Boolean withMarkedAsDeleted) {

        log.info("Finding all cars");

        Page<Car> cars;

        // todo - add role validation if withMarkedAsDeleted is true, access only for admin
        if (withMarkedAsDeleted) {
            cars = carRepository.findAll(pageable);
        } else {
            cars = carRepository.findAllByCarStatusIsNot(CarStatus.DELETED, pageable);
        }

        Page<CarResponseDTO> carResponseDTOPage = new PageImpl<>(cars
                .stream()
                .map(carMapper::carToCarResponseDTO)
                .collect(Collectors.toList()));

        log.info("Find all cars: {}", carResponseDTOPage.getContent());

        return new ResponseEntity<>(carResponseDTOPage, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<CarResponseDTO>> findAllFreeCars(Pageable pageable) {

        log.info("Finding all free not mark as deleted cars");

        Page<CarResponseDTO> carResponseDTOPage =
                new PageImpl<>(carRepository.findAllByCarStatus(CarStatus.FREE, pageable)
                        .stream()
                        .map(carMapper::carToCarResponseDTO)
                        .collect(Collectors.toList()));

        log.info("Find all free not mark as deleted cars: {}", carResponseDTOPage.getContent());

        return new ResponseEntity<>(carResponseDTOPage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarResponseDTO> createNewCar(CreateCarRequestDTO createCarRequestDTO) {

        log.info("Saving car: {}", createCarRequestDTO);

        Car car = carMapper.createCarRequestDTOToCar(createCarRequestDTO);
        car.setDamageStatus("Without damage");
        car.setCarStatus(CarStatus.FREE);
        car = carRepository.save(car);

        log.info("Save car: {}", car);

        return new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarResponseDTO> updateCar(UUID id,
                                                    UpdateCarRequestDTO updateCarRequestDTO) {

        log.info("Updating car: {} with id: {}", updateCarRequestDTO, id);

        Car car = findCarByIdOrThrowException(id);
        CarUtil.getInstance().copyNotNullFieldsFromUpdateCarDTOToCar(updateCarRequestDTO, car);

        log.info("Update car: {}", car);

        return new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarResponseDTO> fixBrokenCar(UUID carId) {

        log.info("Fixing broken car with id: {}", carId);

        Car car = findCarByIdOrThrowException(carId);

        if (car.getCarStatus().equals(CarStatus.DELETED)) {
            throw new BadRequestException(String.format("Unable to fix car. Car with id = %s is deleted", carId));
        }

        if (!car.getCarStatus().equals(CarStatus.BROKEN)) {
            throw new BadRequestException(String.format("Unable to fix car. Car with id = %s already fixed", carId));
        }

        car.setCarStatus(CarStatus.FREE);
        car.setDamageStatus("Without damage");

        log.info("Fix broken car: {}", car);

        return new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CarResponseDTO> setCarAsBroken(UUID carId,
                                                         String damageStatus) {

        log.info("Setting the car as broken with id: {} and damage description: {}", carId, damageStatus);

        Car car = findCarByIdOrThrowException(carId);

        if (car.getCarStatus().equals(CarStatus.BROKEN)) {
            throw new BadRequestException(String.format("Car with id = %s is already broken", carId));
        }

        if (car.getCarStatus().equals(CarStatus.DELETED)) {
            throw new BadRequestException(String.format("Car with id = %s is deleted", carId));
        }

        car.setCarStatus(CarStatus.BROKEN);
        car.setDamageStatus(damageStatus);

        log.info("Set the car: {} as broken", car);

        return new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarResponseDTO> setCarAsBusy(UUID carId) {

        log.info("Setting the car as busy with id: {}", carId);

        Car car = carRepository.findByIdAndCarStatusIsNot(carId, CarStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(Car.class, carId));

        log.info("Found car: {}", car);

        if (car.getCarStatus().equals(CarStatus.BROKEN)) {
            throw new BadRequestException(String.format("Car with id = %s is broken", carId));
        }

        if (car.getCarStatus().equals(CarStatus.BUSY)) {
            throw new BadRequestException(String.format("Car with id = %s is busy now", carId));
        }

        car.setCarStatus(CarStatus.BUSY);

        log.info("Set the car: {} as busy", car);

        return new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarResponseDTO> setCarAsFree(UUID carId) {

        log.info("Setting the car as free with id: {}", carId);

        Car car = findCarByIdOrThrowException(carId);

        log.info("Found car: {}", car);

        if (car.getCarStatus().equals(CarStatus.BROKEN)) {
            throw new BadRequestException(String.format("Car with id = %s is broken", carId));
        }

        if (!car.getCarStatus().equals(CarStatus.BUSY)) {
            throw new BadRequestException(String.format("Car with id = %s is free now", carId));
        }

        car.setCarStatus(CarStatus.FREE);

        log.info("Set the car: {} as free", car);

        return new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<CarResponseDTO> markCarAsDeleted(UUID id) {

        log.info("Marking car with id: {} as deleted", id);

        Car car = findCarByIdOrThrowException(id);

        if (car.getCarStatus().equals(CarStatus.DELETED)) {
            throw new BadRequestException(String.format("Car with id = %s already marked as deleted", id));
        }

        car.setCarStatus(CarStatus.DELETED);

        log.info("Mark car: {} as deleted", car);

        return new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    @Override
    public Car findCarByIdOrThrowException(UUID id) {

        log.info("Finding car with id: {}", id);

        Car car = carRepository.findById(id).orElseThrow(() -> new NotFoundException(
                Car.class, id));

        log.info("Find car: {} with id: {}", car, id);

        return car;
    }
}
