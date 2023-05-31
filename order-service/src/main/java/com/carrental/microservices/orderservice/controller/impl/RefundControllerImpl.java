package com.carrental.microservices.orderservice.controller.impl;

import com.carrental.microservices.orderservice.controller.RefundController;
import com.carrental.microservices.orderservice.domain.dto.request.CreateRefundRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.RefundResponseDTO;
import com.carrental.microservices.orderservice.service.RefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Implementation class for RefundController.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class RefundControllerImpl implements RefundController {

    private final RefundService refundService;

    @Override
    public ResponseEntity<RefundResponseDTO> findRefundByOrderId(UUID orderId) {

        log.info("GET request to find order's refund with orderId: {}", orderId);

        return refundService.findRefundByOrderId(orderId);
    }

    @Override
    public ResponseEntity<RefundResponseDTO> createRefund(CreateRefundRequestDTO createRefundRequestDTO) {

        log.info("POST request to create refund: {}", createRefundRequestDTO);

        return refundService.createRefund(createRefundRequestDTO);
    }
}
