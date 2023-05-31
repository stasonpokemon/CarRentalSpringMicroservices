package com.carrental.microservices.orderservice.controller;

import com.carrental.microservices.orderservice.domain.dto.request.CreateRefundRequestDTO;
import com.carrental.microservices.orderservice.domain.dto.response.RefundResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * Interface that presents basic endpoints for working with Refund entity.
 */
@RequestMapping("/refunds")
@Tag(name = "Refund Controller", description = "Refund management controller")
public interface RefundController {


    @Operation(
            summary = "Find refund by id order's id",
            description = "This endpoint allows you to get order's refund by order's id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following refund",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefundResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bed request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/order/{id}")
    ResponseEntity<RefundResponseDTO> findRefundByOrderId(
            @PathVariable("id") UUID orderId);

    @Operation(
            summary = "Create refund",
            description = "This endpoint allows you to create a new order's refund in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Refund successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefundResponseDTO.class))}),
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
    @PostMapping
    ResponseEntity<RefundResponseDTO> createRefund
            (@RequestBody @Valid CreateRefundRequestDTO createRefundRequestDTO);
}
