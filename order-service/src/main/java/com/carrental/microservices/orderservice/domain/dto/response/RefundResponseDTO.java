package com.carrental.microservices.orderservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_UUID_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_DESCRIPTION_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_STATUS;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_STATUS_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_PRICE;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_PRICE_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_REFUND_DATE;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_REFUND_DATE_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_UUID_DESCRIPTION;

/**
 * This class presents a DTO, which is available via OrderController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponseDTO {

    @Schema(example = REFUND_UUID, description = REFUND_UUID_DESCRIPTION)
    @JsonProperty(value = "refund_id")
    private UUID id;
    @Schema(example = REFUND_DAMAGE_STATUS, description = REFUND_DAMAGE_STATUS_DESCRIPTION)
    @JsonProperty(value = "is_damaged")
    private Boolean damage;

    @Schema(example = REFUND_DAMAGE_DESCRIPTION, description = REFUND_DAMAGE_DESCRIPTION_DESCRIPTION)
    @JsonProperty(value = "damage_description")
    private String damageDescription;

    @Schema(example = REFUND_PRICE, description = REFUND_PRICE_DESCRIPTION)
    @JsonProperty(value = "price")
    private double price;

    @Schema(example = REFUND_REFUND_DATE, description = REFUND_REFUND_DATE_DESCRIPTION)
    @JsonProperty(value = "refund_date")
    private LocalDateTime refundDate;

    @Schema(example = ORDER_UUID, description = ORDER_UUID_DESCRIPTION)
    @JsonProperty(value = "order_id")
    private UUID orderId;
}
