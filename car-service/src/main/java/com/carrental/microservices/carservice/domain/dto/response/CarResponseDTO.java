package com.carrental.microservices.carservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_BROKEN;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_BROKEN_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_IMAGE_LINK;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_IMAGE_LINK_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_MODEL;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_MODEL_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_PRODUCER;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_PRODUCER_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_RELEASE_DATE;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_RELEASE_DATE_DESCRIPTION;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_UUID;
import static com.carrental.microservices.carservice.util.swagger.OpenApiConstants.CAR_UUID_DESCRIPTION;


/**
 * This class presents a DTO, which is available via CarController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDTO {

    @Schema(example = CAR_UUID, description = CAR_UUID_DESCRIPTION)
    @JsonProperty(value = "car_id")
    private UUID id;
    @Schema(example = CAR_PRODUCER, description = CAR_PRODUCER_DESCRIPTION)
    @JsonProperty(value = "producer")
    private String producer;

    @Schema(example = CAR_MODEL, description = CAR_MODEL_DESCRIPTION)
    @JsonProperty(value = "model")
    private String model;

    @Schema(example = CAR_RELEASE_DATE, description = CAR_RELEASE_DATE_DESCRIPTION)
    @JsonProperty(value = "release_date")
    private LocalDate releaseDate;

    @Schema(example = CAR_PRICE_PER_DAY, description = CAR_PRICE_PER_DAY_DESCRIPTION)
    @JsonProperty(value = "price_per_day")
    private Double pricePerDay;

    @Schema(example = CAR_EMPLOYMENT_STATUS, description = CAR_EMPLOYMENT_STATUS_DESCRIPTION)
    @JsonProperty(value = "is_busy")
    private boolean busy;

    @Schema(example = CAR_DAMAGE_STATUS, description = CAR_DAMAGE_STATUS_DESCRIPTION)
    @JsonProperty(value = "damage_status")
    private String damageStatus;

    @Schema(example = CAR_IMAGE_LINK, description = CAR_IMAGE_LINK_DESCRIPTION)
    @JsonProperty(value = "image_link")
    private String imageLink;

    @Schema(example = CAR_BROKEN, description = CAR_BROKEN_DESCRIPTION)
    @JsonProperty(value = "is_broken")
    private boolean broken;
}
