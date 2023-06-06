package com.carrental.microservices.carservice.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * KafkaOrderCarStatusConsumerProperties class that provides access to the kafka Consumer properties.
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka.order-car-status.consumer")
public class KafkaOrderCarStatusConsumerProperties {

    private String topic;

    private String groupId;
}
