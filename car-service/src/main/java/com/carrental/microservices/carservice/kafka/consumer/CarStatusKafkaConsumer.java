package com.carrental.microservices.carservice.kafka.consumer;

import com.carrental.microservices.carservice.kafka.messages.CarStatus;
import com.carrental.microservices.carservice.kafka.messages.OrderCarStatusKafkaMessage;
import com.carrental.microservices.carservice.kafka.properties.KafkaOrderCarStatusConsumerProperties;
import com.carrental.microservices.carservice.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CarStatusKafkaConsumer {

    private final KafkaOrderCarStatusConsumerProperties kafkaOrderCarStatusConsumerProperties;

    private final CarService carService;


    @KafkaListener(
            topics = "${spring.kafka.order-car-status.consumer.topic}",
            groupId = "${spring.kafka.order-car-status.consumer.groupId}",
            containerFactory = "listenerContainerFactory")
    void listener(OrderCarStatusKafkaMessage message) {

        if (message.getCarStatus().equals(CarStatus.BUSY)){
            carService.setCarAsBusy(message.getCarId());
        } else if (message.getCarStatus().equals(CarStatus.FREE)){
            carService.setCarAsFree(message.getCarId());
        } else if (message.getCarStatus().equals(CarStatus.BROKEN)){
            carService.setCarAsBroken(message.getCarId(), message.getDamageDescription());
        }

        log.info("Listen message: {} from topic: {}", message,
                kafkaOrderCarStatusConsumerProperties.getTopic());
    }
}
