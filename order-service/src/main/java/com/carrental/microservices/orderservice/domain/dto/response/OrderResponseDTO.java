package com.carrental.microservices.orderservice.domain.dto.response;


import com.carrental.microservices.orderservice.domain.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_UUID_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_ORDER_DATE;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_ORDER_DATE_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_ORDER_STATUS;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_ORDER_STATUS_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_PRICE;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_PRICE_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_RENTAL_PERIOD;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_RENTAL_PERIOD_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_UUID_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_UUID_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_UUID_DESCRIPTION;


/**
 * This class presents a DTO, which is available via OrderController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    @Schema(example = ORDER_UUID, description = ORDER_UUID_DESCRIPTION)
    @JsonProperty(value = "order_id")
    private UUID id;

    @Schema(example = CAR_UUID, description = CAR_UUID_DESCRIPTION)
    @JsonProperty(value = "car_id")
    private UUID carId;

    @Schema(example = USER_UUID, description = USER_UUID_DESCRIPTION)
    @JsonProperty(value = "user_id")
    private UUID userId;

    @Schema(example = REFUND_UUID, description = REFUND_UUID_DESCRIPTION)
    @JsonProperty(value = "refund_id")
    private UUID refundId;

    @Schema(example = ORDER_PRICE, description = ORDER_PRICE_DESCRIPTION)
    @JsonProperty(value = "price")
    private double price;

    @Schema(example = ORDER_ORDER_STATUS, description = ORDER_ORDER_STATUS_DESCRIPTION)
    @JsonProperty(value = "order_status")
    private OrderStatus orderStatus;

    @Schema(example = ORDER_ORDER_DATE, description = ORDER_ORDER_DATE_DESCRIPTION)
    @JsonProperty(value = "order_date")
    private LocalDateTime orderDate;

    @Schema(example = ORDER_RENTAL_PERIOD, description = ORDER_RENTAL_PERIOD_DESCRIPTION)
    @JsonProperty(value = "rental_period")
    private Integer rentalPeriod;
}
