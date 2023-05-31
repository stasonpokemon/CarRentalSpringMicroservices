package com.carrental.microservices.orderservice.feign;

import com.carrental.microservices.orderservice.domain.dto.request.UpdateCarRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Component
@Validated
@FeignClient(name = "car-service")
public interface CarServiceFeignClient {

    @GetMapping("/cars/{id}")
    @Valid
    CarResponseDTO findCarByIdNotMarkedAsDeleted(@PathVariable(name = "id") UUID uuid);

    @PatchMapping("/cars/{id}")
    @Valid
    CarResponseDTO updateCar(@PathVariable("id") UUID carId,
                             @RequestBody @Valid UpdateCarRequestDTO updateCarRequestDTO);

    @PatchMapping("/cars/{id}/busy")
    CarResponseDTO updateCarStatusAsBusy(@PathVariable("id") UUID carId);

    @PatchMapping("/cars/{id}/free")
    CarResponseDTO updateCarStatusAsFree(@PathVariable("id") UUID carId);

    @PatchMapping("/cars/{id}/broken")
    CarResponseDTO setCarAsBroken(
            @PathVariable("id") UUID carId,
            @RequestParam(name = "damage") String damageStatus);
}
