package com.carrental.microservices.orderservice.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.ORDER_UUID_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_DESCRIPTION_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_STATUS;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_DAMAGE_STATUS_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_PRICE;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.REFUND_PRICE_DESCRIPTION;


/**
 * This class presents a DTO, which is available via OrderController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRefundRequestDTO {

    @NotNull(message = "isDamaged field must be filled")
    @Schema(example = REFUND_DAMAGE_STATUS, description = REFUND_DAMAGE_STATUS_DESCRIPTION)
    @JsonProperty(value = "is_damaged")
    private Boolean damage;

    @Schema(example = REFUND_DAMAGE_DESCRIPTION, description = REFUND_DAMAGE_DESCRIPTION_DESCRIPTION)
    @JsonProperty(value = "damage_description")
    private String damageDescription;

    @Min(value = 0, message = "Price must be 0 or greater than 0")
    @Schema(example = REFUND_PRICE, description = REFUND_PRICE_DESCRIPTION)
    @JsonProperty(value = "price")
    private double price;

    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
            message = "Invalid order id")
    @NotBlank(message = "Order id can't be null")
    @Schema(example = ORDER_UUID, description = ORDER_UUID_DESCRIPTION)
    @JsonProperty(value = "order_id")
    private String orderId;

}
