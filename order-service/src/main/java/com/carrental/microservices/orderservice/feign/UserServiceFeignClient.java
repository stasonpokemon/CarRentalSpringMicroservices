package com.carrental.microservices.orderservice.feign;

import com.carrental.microservices.orderservice.domain.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Component
@Validated
@FeignClient(name = "user-service")
public interface UserServiceFeignClient {

    @GetMapping("/users/{id}")
    @Valid
    UserResponseDTO findUserById(@PathVariable(name = "id") UUID uuid);
}
