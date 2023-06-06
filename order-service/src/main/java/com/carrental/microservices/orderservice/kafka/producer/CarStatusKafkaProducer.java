package com.carrental.microservices.orderservice.kafka.producer;

import com.carrental.microservices.orderservice.kafka.messages.CarStatusMessage;
import com.carrental.microservices.orderservice.kafka.properties.KafkaOrderCarStatusProducerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * CarStatusProducer class that can send message to kafka consumer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CarStatusKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaOrderCarStatusProducerProperties kafkaOrderCarStatusProducerProperties;

    public void sendMessage(CarStatusMessage carStatusMessage) {

        String topicName = kafkaOrderCarStatusProducerProperties.getTopic();

        log.info("Trying to send message: {} to topic: {}", carStatusMessage, topicName);

        kafkaTemplate.send(topicName, carStatusMessage);

        log.info("Sent send message: {} to topic: {}", carStatusMessage, topicName);
    }
}
