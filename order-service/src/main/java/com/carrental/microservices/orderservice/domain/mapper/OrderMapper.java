package com.carrental.microservices.orderservice.domain.mapper;

import com.carrental.microservices.orderservice.domain.entity.Order;
import com.carrental.microservices.orderservice.domain.dto.request.CreateOrderRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

/**
 * This interface presents the basic contract for converting Order to OrderDTO and vice versa.
 */
@Mapper
public interface OrderMapper {


    @Mapping(target = "refundId", source = "refund.id")
    OrderResponseDTO orderToOrderResponseDTO(Order order);

    @Mapping(target = "carId", expression = "java(map(createOrderRequestDTO.getCarId()))")
    @Mapping(target = "userId", expression = "java(map(createOrderRequestDTO.getUserId()))")
    Order createOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO);

    default UUID map(String uuid){
        return UUID.fromString(uuid);
    }
}
