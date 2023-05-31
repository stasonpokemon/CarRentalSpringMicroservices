package com.carrental.microservices.orderservice.domain.mapper;

import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;
import com.carrental.microservices.orderservice.domain.dto.request.UpdateCarRequestDTO;
import org.mapstruct.Mapper;

/**
 * This interface presents the basic contract for converting Car to CarDTO and vice versa.
 */
@Mapper
public interface CarMapper {

    UpdateCarRequestDTO carResponseDTOtoUpdateCarRequestDTO(CarResponseDTO carResponseDTO);

}
