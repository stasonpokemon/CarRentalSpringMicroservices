package com.carrental.microservices.orderservice.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_IMAGE_LINK;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_IMAGE_LINK_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_MODEL;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_MODEL_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_PRODUCER;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_PRODUCER_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_RELEASE_DATE;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.CAR_RELEASE_DATE_DESCRIPTION;


/**
 * This class presents a DTO, which is available via CarController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCarRequestDTO {

    @NotBlank(message = "Please fill the producer")
    @Length(max = 255, message = "Producer too long. Max length is 2048")
    @Schema(example = CAR_PRODUCER, description = CAR_PRODUCER_DESCRIPTION)
    @JsonProperty(value = "producer")
    private String producer;

    @NotBlank(message = "Please fill the model")
    @Length(max = 255, message = "Model too long. Max length is 2048")
    @Schema(example = CAR_MODEL, description = CAR_MODEL_DESCRIPTION)
    @JsonProperty(value = "model")
    private String model;

    @NotNull(message = "Please fill the release date")
    @Schema(example = CAR_RELEASE_DATE, description = CAR_RELEASE_DATE_DESCRIPTION)
    @JsonProperty(value = "release_date")
    private LocalDate releaseDate;

    @NotNull(message = "Please fill the price per day")
    @Min(value = 0, message = "Price per day can't be less than 0")
    @Schema(example = CAR_PRICE_PER_DAY, description = CAR_PRICE_PER_DAY_DESCRIPTION)
    @JsonProperty(value = "price_per_day")
    private Double pricePerDay;

    @NotNull(message = "Employment status can't be null")
    @Schema(example = CAR_EMPLOYMENT_STATUS, description = CAR_EMPLOYMENT_STATUS_DESCRIPTION)
    @JsonProperty(value = "is_busy")
    private boolean busy;

    @Length(max = 255, message = "Damage status too long. Max length is 1000")
    @NotBlank(message = "Please fill the damage status")
    @Schema(example = CAR_DAMAGE_STATUS, description = CAR_DAMAGE_STATUS_DESCRIPTION)
    @JsonProperty(value = "damage_status")
    private String damageStatus;

    @NotBlank(message = "Please fill the image link")
    @Schema(example = CAR_IMAGE_LINK, description = CAR_IMAGE_LINK_DESCRIPTION)
    @JsonProperty(value = "image_link")
    private String imageLink;
}

