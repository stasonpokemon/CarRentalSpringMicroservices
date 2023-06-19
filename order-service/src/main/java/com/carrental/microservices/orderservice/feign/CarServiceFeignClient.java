package com.carrental.microservices.orderservice.feign;

import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * CarServiceFeignClient feign client class.
 */
@Component
@Validated
@FeignClient(name = "car-service")
public interface CarServiceFeignClient {

    @GetMapping("/cars/{id}")
    @Valid
    CarResponseDTO findCarByIdNotMarkedAsDeleted(@PathVariable(name = "id") UUID uuid);
}
