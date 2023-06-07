package com.carrental.microservices.orderservice.kafka.messages;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.UUID;

/**
 * CarStatusMessage class for kafka message.
 */
@Data
@Builder
@Jacksonized
public class CarStatusMessage implements Serializable {

    private UUID carId;

    private CarStatus carStatus;

    private String damageDescription;
}
