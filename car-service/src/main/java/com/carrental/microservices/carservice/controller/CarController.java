package com.carrental.microservices.carservice.controller;

import com.carrental.microservices.carservice.domain.dto.request.CreateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.request.UpdateCarRequestDTO;
import com.carrental.microservices.carservice.domain.dto.response.CarResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Interface that presents basic endpoints for working with Car entity.
 */
@RequestMapping("/cars")
@Tag(name = "Car Controller", description = "Car management controller")
public interface CarController {

    @Operation(
            summary = "Find all cars",
            description = "This endpoint allows you to get all cars from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following cars",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarResponseDTO.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping()
    ResponseEntity<Page<CarResponseDTO>> findAll(
            @ParameterObject @PageableDefault Pageable pageable,
            @RequestParam(name = "with_deleted", required = false, defaultValue = "false")
            Boolean withMarkedAsDeleted);

    @Operation(
            summary = "Find car by id",
            description = "This endpoint allows you to get car by id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following car",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bed request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            value = "{\"timestamp\": \"2023-02-18T01:20:33.0725725\","
                                                            + "\"message\": \"Not found Car with id: " +
                                                    "695fedf4-bd75-4b38-bfcc-5f498b333021\"}")}
                    )}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    ResponseEntity<CarResponseDTO> findCar(
            @Parameter(description = "car id", required = true) @PathVariable(name = "id") UUID carId,
            @RequestParam(name = "with_deleted", required = false, defaultValue = "false")
            Boolean withMarkedAsDeleted);

    @Operation(
            summary = "Create new car",
            description = "This endpoint allows you to create a new car in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @PostMapping
    ResponseEntity<CarResponseDTO> createCar(
            @RequestBody @Valid CreateCarRequestDTO createCarRequestDTO);

    @Operation(
            summary = "Update car",
            description = "This endpoint allows you to update an already existing car in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @PatchMapping("/{id}")
    ResponseEntity<CarResponseDTO> updateCar(
            @PathVariable("id") UUID carId,
            @RequestBody @Valid UpdateCarRequestDTO updateCarRequestDTO);

    @Operation(
            summary = "Fix broken car",
            description = "This endpoint allows you to fix broken car that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully fixed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @PatchMapping("/{id}/fix")
    ResponseEntity<CarResponseDTO> fixBrokenCar(
            @PathVariable("id") UUID carId);

    @Operation(
            summary = "Set the car as broken",
            description = "This endpoint allows you to set the car as broken and set damage description that " +
                    "not busy at the moment, not fixed and already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car status is set as broken",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @PatchMapping("/{id}/broken")
    ResponseEntity<CarResponseDTO> setCarAsBroken(
            @PathVariable("id") UUID carId,
            @RequestParam(name = "damage") String damageStatus);

    @PatchMapping("/{id}/busy")
    ResponseEntity<CarResponseDTO> setCarAsBusy(@PathVariable("id") UUID carId);

    @PatchMapping("/{id}/free")
    ResponseEntity<CarResponseDTO> setCarAsFree(@PathVariable("id") UUID carId);

    @Operation(
            summary = "Mark car as deleted",
            description = "This endpoint allows you to mark car as deleted that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully mark as deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/{id}")
    ResponseEntity<CarResponseDTO> markCarAsDeleted(
            @PathVariable("id") UUID carId);
}
