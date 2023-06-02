package com.carrental.microservices.orderservice.service;

import com.carrental.microservices.orderservice.domain.dto.request.CreateOrderRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;
import com.carrental.microservices.orderservice.domain.dto.response.OrderResponseDTO;
import com.carrental.microservices.orderservice.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * The OrderService interface, which stores the business logic for working with an order.
 */
public interface OrderService {

    Order findOrderWithOrderStatusUnderConsideration(UUID orderId);

    ResponseEntity<Page<OrderResponseDTO>> findAllOrders(Pageable pageable);

    ResponseEntity<Page<OrderResponseDTO>> findAllOrdersByUserId(UUID userId, Pageable pageable);

    ResponseEntity<OrderResponseDTO> findOrderById(UUID orderId);

    ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO);

    Order saveNewOrderWithOrderStatusUnderConsideration(Order order, CarResponseDTO car, UUID userId);

    ResponseEntity<OrderResponseDTO> acceptOrder(UUID orderId);

    ResponseEntity<OrderResponseDTO> cancelOrder(UUID orderId);

    Order setOrderStatusRefusal(Order order);

    Order findOrderWithoutRefundById(UUID orderId);

    Order findOrderByIdOrThrowException(UUID orderId);

    Order findOrderWithOrderStatusConfirmed(UUID orderId);
}
