package com.carrental.microservices.orderservice.service.impl;

import com.carrental.microservices.orderservice.domain.dto.request.CreateOrderRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.CarResponseDTO;
import com.carrental.microservices.orderservice.domain.dto.response.OrderResponseDTO;
import com.carrental.microservices.orderservice.domain.dto.response.UserResponseDTO;
import com.carrental.microservices.orderservice.domain.entity.Order;
import com.carrental.microservices.orderservice.domain.entity.OrderStatus;
import com.carrental.microservices.orderservice.domain.mapper.OrderMapper;
import com.carrental.microservices.orderservice.exception.BadRequestException;
import com.carrental.microservices.orderservice.exception.NotFoundException;
import com.carrental.microservices.orderservice.repo.OrderRepository;
import com.carrental.microservices.orderservice.service.CarService;
import com.carrental.microservices.orderservice.service.OrderService;
import com.carrental.microservices.orderservice.service.UserService;
import com.carrental.microservices.orderservice.util.Extractor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation class for OrderService.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    private final CarService carService;

    private final UserService userService;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(@Lazy OrderService orderService, OrderRepository orderRepository, CarService carService,
                            UserService userService) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.carService = carService;
        this.userService = userService;
        this.orderMapper = Mappers.getMapper(OrderMapper.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<OrderResponseDTO> findOrderById(UUID orderId) {

        log.info("Finding order by id: {}", orderId);

        OrderResponseDTO orderResponseDTO =
                orderMapper.orderToOrderResponseDTO(findOrderByIdOrThrowException(orderId));

        ResponseEntity<OrderResponseDTO> response = new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);

        log.info("Find order by id: {}", orderId);

        return response;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Order findOrderWithoutRefundById(UUID orderId) {
        Order order = findOrderByIdOrThrowException(orderId);

        if (order.getRefund() != null) {
            throw new BadRequestException(
                    String.format("Order with id = %s already has refund", orderId));
        }

        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Order findOrderWithOrderStatusConfirmed(UUID orderId) {
        Order order = findOrderWithoutRefundById(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
            throw new BadRequestException("Order with id: %s has status - %s"
                    .formatted(orderId, order.getOrderStatus()));
        }

        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Order findOrderWithOrderStatusUnderConsideration(UUID orderId) {
        Order order = findOrderWithoutRefundById(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.UNDER_CONSIDERATION)) {
            throw new BadRequestException(
                    String.format("Order with id = %s isn't under consideration", orderId));
        }

        return order;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<OrderResponseDTO>> findAllOrders(Pageable pageable) {

        log.info("Finding all orders");

        Page<OrderResponseDTO> orderResponseDTOPage = new PageImpl<>(orderRepository.findAll(pageable)
                .stream()
                .map(orderMapper::orderToOrderResponseDTO)
                .collect(Collectors.toList()));

        ResponseEntity<Page<OrderResponseDTO>> response = new ResponseEntity<>(orderResponseDTOPage, HttpStatus.OK);

        log.info("Find all orders");

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<OrderResponseDTO>> findAllOrdersByUserId(UUID userId,
                                                                        Pageable pageable) {

        log.info("Finding all orders by user id: {}", userId);

        UserResponseDTO user = userService.findUserById(userId);

        log.info("Found user: {}", user);

        Page<OrderResponseDTO> orderResponseDTOPage = new PageImpl<>(orderRepository.findAllByUserId(userId)
                .stream()
                .map(orderMapper::orderToOrderResponseDTO)
                .collect(Collectors.toList()));

        ResponseEntity<Page<OrderResponseDTO>> response = new ResponseEntity<>(orderResponseDTOPage, HttpStatus.OK);

        log.info("Find all orders by user id: {}", userId);

        return response;
    }

    @Override
    public ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO) {

        log.info("Creating new order: {}", createOrderRequestDTO);

        UUID carId = Extractor.extractUUIDFromString(createOrderRequestDTO.getCarId());
        UUID userId = Extractor.extractUUIDFromString(createOrderRequestDTO.getUserId());

        UserResponseDTO user = userService.findUserWithPassportById(userId);

        log.info("Found user: {}", user);

        CarResponseDTO car = carService.findRepairedAndFreeCarById(carId);

        log.info("Found car: {}", car);

        Order order = orderMapper.createOrderRequestDTOToOrder(createOrderRequestDTO);

        order = orderService.saveNewOrderWithOrderStatusUnderConsideration(order, car, userId);

        carService.updateCarStatusAsBusy(order.getCarId());

        log.info("Creat new order: {}", order);

        return new ResponseEntity<>(orderMapper.orderToOrderResponseDTO(order), HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<OrderResponseDTO> acceptOrder(UUID orderId) {

        log.info("Accepting order with id: {}", orderId);

        Order order = findOrderWithOrderStatusUnderConsideration(orderId);

        order.setOrderStatus(OrderStatus.CONFIRMED);

        log.info("Accept order with id: {}", orderId);

        return new ResponseEntity<>(orderMapper.orderToOrderResponseDTO(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponseDTO> cancelOrder(UUID orderId) {

        // Обновление заказа transactional
        log.info("Canceling order with id: {}", orderId);

        Order order = findOrderWithOrderStatusUnderConsideration(orderId);

        order = orderService.setOrderStatusRefusal(order);

        log.info("Successful setting order status as refusal");

        carService.updateCarStatusAsFree(order.getCarId());

        log.info("Successful setting car status as free");


        // todo сделать алерт менеджеру при рассинхроне

        log.info("Cancel order with id: {}", orderId);

        return new ResponseEntity<>(orderMapper.orderToOrderResponseDTO(order), HttpStatus.OK);
    }

    @Override
    @Transactional
    public Order setOrderStatusRefusal(Order order) {

        log.info("Trying to set order status as refusal");

        order.setOrderStatus(OrderStatus.REFUSAL);

        return order;
    }

    @Override
    @Transactional
    public Order saveNewOrderWithOrderStatusUnderConsideration(Order order,
                                                               CarResponseDTO car, UUID userId) {
        order.setCarId(car.getId());
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.UNDER_CONSIDERATION);
        order.setOrderDate(LocalDateTime.now());
        order.setPrice(((double) order.getRentalPeriod() * car.getPricePerDay()));

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Order findOrderByIdOrThrowException(UUID orderId) {

        log.info("Finding order with id: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(Order.class, orderId));

        log.info("Find order with id: {}", orderId);

        return order;
    }
}

