package com.carrental.microservices.carservice.domain.dto.response;

import com.carrental.microservices.carservice.domain.entity.CarStatus;
import com.carrental.microservices.carservice.util.swagger.OpenApiConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


/**
 * This class presents a DTO, which is available via CarController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDTO {

    @Schema(example = OpenApiConstants.CAR_UUID, description = OpenApiConstants.CAR_UUID_DESCRIPTION)
    @JsonProperty(value = "car_id")
    private UUID id;
    @Schema(example = OpenApiConstants.CAR_PRODUCER, description = OpenApiConstants.CAR_PRODUCER_DESCRIPTION)
    @JsonProperty(value = "producer")
    private String producer;

    @Schema(example = OpenApiConstants.CAR_MODEL, description = OpenApiConstants.CAR_MODEL_DESCRIPTION)
    @JsonProperty(value = "model")
    private String model;

    @Schema(example = OpenApiConstants.CAR_RELEASE_DATE, description = OpenApiConstants.CAR_RELEASE_DATE_DESCRIPTION)
    @JsonProperty(value = "release_date")
    private LocalDate releaseDate;

    @Schema(example = OpenApiConstants.CAR_PRICE_PER_DAY, description = OpenApiConstants.CAR_PRICE_PER_DAY_DESCRIPTION)
    @JsonProperty(value = "price_per_day")
    private Double pricePerDay;

    @Schema(example = OpenApiConstants.CAR_STATUS, description = OpenApiConstants.CAR_STATUS_DESCRIPTION)
    @JsonProperty(value = "car_status")
    private CarStatus carStatus;

    @Schema(example = OpenApiConstants.CAR_DAMAGE_STATUS, description = OpenApiConstants.CAR_DAMAGE_STATUS_DESCRIPTION)
    @JsonProperty(value = "damage_status")
    private String damageStatus;

    @Schema(example = OpenApiConstants.CAR_IMAGE_LINK, description = OpenApiConstants.CAR_IMAGE_LINK_DESCRIPTION)
    @JsonProperty(value = "image_link")
    private String imageLink;
}
