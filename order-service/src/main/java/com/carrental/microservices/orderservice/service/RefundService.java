package com.carrental.microservices.orderservice.service;

import com.carrental.microservices.orderservice.domain.entity.Order;
import com.carrental.microservices.orderservice.domain.entity.Refund;
import com.carrental.microservices.orderservice.domain.dto.request.CreateRefundRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.RefundResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface RefundService {

    Refund saveNewRefundWithoutDamage(Refund refund, Order order);

    Refund saveNewRefundWithDamage(Refund refund, Order order);

    ResponseEntity<RefundResponseDTO> findRefundByOrderId(UUID orderId);

    ResponseEntity<RefundResponseDTO> createRefund(CreateRefundRequestDTO createRefundRequestDTO);

}
