package com.carrental.microservices.carservice.domain.mapper;

import com.carrental.microservices.carservice.domain.entity.Car;
import com.carrental.microservices.carservice.domain.dto.request.CreateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.response.CarResponseDTO;
import org.mapstruct.Mapper;

/**
 * This interface presents the basic contract for converting Car to CarDTO and vice versa.
 */
@Mapper
public interface CarMapper {

    CarResponseDTO carToCarResponseDTO(Car car);

    Car createCarRequestDTOToCar(CreateCarRequestDTO createCarRequestDTO);

    CreateCarRequestDTO carToCreateCarRequestDTO(Car car);

    Car carResponseDTOToCar(CarResponseDTO carResponseDTO);
}
