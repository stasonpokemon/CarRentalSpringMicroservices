package com.carrental.microservices.carservice.kafka.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class OrderCarStatusKafkaMessage implements Serializable {

    private UUID carId;

    private CarStatus carStatus;

    private String damageDescription;
}
