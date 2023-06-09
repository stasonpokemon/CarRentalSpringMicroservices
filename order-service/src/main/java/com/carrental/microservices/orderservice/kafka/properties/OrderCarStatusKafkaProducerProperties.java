package com.carrental.microservices.orderservice.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * KafkaOrderCarStatusProducerProperties class that provides access to the kafka Producer properties.
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka.order-car-status.producer")
public class OrderCarStatusKafkaProducerProperties {

    private String topic;
}
