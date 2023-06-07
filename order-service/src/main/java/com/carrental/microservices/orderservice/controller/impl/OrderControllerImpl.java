package com.carrental.microservices.orderservice.controller.impl;

import com.carrental.microservices.orderservice.controller.OrderController;
import com.carrental.microservices.orderservice.domain.dto.request.CreateOrderRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.OrderResponseDTO;
import com.carrental.microservices.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Implementation class for OrderController.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {


    private final OrderService orderService;

    @Override
    public ResponseEntity<Page<OrderResponseDTO>> findAll(Pageable pageable) {

        log.info("GET request to find all orders");

        return orderService.findAllOrders(pageable);
    }

    @Override
    public ResponseEntity<?> findById(UUID orderId) {

        log.info("GET request to find order with id: {}", orderId);

        return orderService.findOrderById(orderId);
    }

    @Override
    public ResponseEntity<?> findAllByUserId(UUID userId, Pageable pageable) {

        log.info("GET request to find orders by user id: {}", userId);

        return orderService.findAllOrdersByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO) {

        log.info("POST request to create order: {}",
                createOrderRequestDTO);

        return orderService.createOrder(createOrderRequestDTO);
    }

    @Override
    public ResponseEntity<OrderResponseDTO> acceptOrder(UUID orderId) {

        log.info("PATCH request to accept order with id: {}", orderId);

        return orderService.acceptOrder(orderId);
    }

    @Override
    public ResponseEntity<OrderResponseDTO> cancelOrder(UUID orderId) {

        log.info("PATCH request to cancel order with id: {}", orderId);

        return orderService.cancelOrder(orderId);
    }
}
