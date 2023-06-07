package com.carrental.microservices.orderservice.service.impl;

import com.carrental.microservices.orderservice.domain.dto.request.CreateRefundRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.RefundResponseDTO;
import com.carrental.microservices.orderservice.domain.entity.Order;
import com.carrental.microservices.orderservice.domain.entity.Refund;
import com.carrental.microservices.orderservice.domain.mapper.RefundMapper;
import com.carrental.microservices.orderservice.exception.NotFoundException;
import com.carrental.microservices.orderservice.repo.OrderRepository;
import com.carrental.microservices.orderservice.service.CarService;
import com.carrental.microservices.orderservice.service.OrderService;
import com.carrental.microservices.orderservice.service.RefundService;
import com.carrental.microservices.orderservice.util.Extractor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation class for RefundService.
 */
@Service
@Slf4j
public class RefundServiceImpl implements RefundService {

    private final RefundService refundService;

    private final OrderService orderService;

    private final CarService carService;

    private final OrderRepository orderRepository;

    private final RefundMapper refundMapper;

    public RefundServiceImpl(@Lazy RefundService refundService, OrderService orderService,
                             CarService carService, OrderRepository orderRepository) {
        this.refundService = refundService;
        this.orderService = orderService;
        this.carService = carService;
        this.orderRepository = orderRepository;
        this.refundMapper = Mappers.getMapper(RefundMapper.class);
    }


    @Override
    @Transactional
    public Refund saveNewRefundWithoutDamage(Refund refund, Order order) {
        refund.setRefundDate(LocalDateTime.now());
        refund.setPrice(0);
        refund.setDamageDescription("");
        refund.setOrder(order);

        order.setRefund(refund);

        return orderRepository.save(order).getRefund();
    }

    @Override
    @Transactional
    public Refund saveNewRefundWithDamage(Refund refund, Order order) {
        refund.setRefundDate(LocalDateTime.now());
        refund.setOrder(order);

        order.setRefund(refund);

        return orderRepository.save(order).getRefund();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<RefundResponseDTO> findRefundByOrderId(UUID orderId) {

        log.info("Trying to find order's refund by orderId: {}", orderId);

        Order order = orderService.findOrderByIdOrThrowException(orderId);

        if (order.getRefund() == null) {
            throw new NotFoundException(
                    String.format("Order with id = %s hasn't refund", orderId));
        }

        RefundResponseDTO refundResponseDTO = refundMapper.refundToRefundResponseDTO(order.getRefund());

        ResponseEntity<RefundResponseDTO> response = new ResponseEntity<>(refundResponseDTO, HttpStatus.OK);

        log.info("Fond order's refund: {}", refundResponseDTO);

        return response;
    }

    @Override
    public ResponseEntity<RefundResponseDTO> createRefund(CreateRefundRequestDTO createRefundRequestDTO) {

        log.info("Trying to create refund: {}", createRefundRequestDTO);

        UUID orderId = Extractor.extractUUIDFromString(createRefundRequestDTO.getOrderId());

        Order order = orderService.findOrderWithOrderStatusConfirmed(orderId);

        Refund refund = refundMapper.createRefundRequestDTOToRefund(createRefundRequestDTO);

        UUID carId = order.getCarId();

        if (!createRefundRequestDTO.getDamage()) {

            refund = refundService.saveNewRefundWithoutDamage(refund, order);

            carService.updateCarStatusAsFree(carId);

        } else {

            refund = refundService.saveNewRefundWithDamage(refund, order);

            carService.updateCarStatusAsBroken(carId, refund.getDamageDescription());
        }

        RefundResponseDTO refundResponseDTO = refundMapper.refundToRefundResponseDTO(refund);

        ResponseEntity<RefundResponseDTO> response =
                new ResponseEntity<>(refundResponseDTO, HttpStatus.OK);

        log.info("Create refund: {}", refundResponseDTO);

        return response;
    }

}
