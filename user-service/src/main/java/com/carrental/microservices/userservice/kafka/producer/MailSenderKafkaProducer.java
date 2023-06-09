package com.carrental.microservices.userservice.kafka.producer;

import com.carrental.microservices.userservice.kafka.message.MailKafkaMessage;
import com.carrental.microservices.userservice.kafka.properties.UserNotificationProducerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * MailSenderKafkaProducer class that can send message to kafka consumer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final UserNotificationProducerProperties userNotificationProducerProperties;


    public void sendMessage(MailKafkaMessage mailKafkaMessage) {

        String topicName = userNotificationProducerProperties.getTopic();

        log.info("Trying to send message: {} to topic: {}", mailKafkaMessage, topicName);

        kafkaTemplate.send(topicName, mailKafkaMessage);

        log.info("Sent send message: {} to topic: {}", mailKafkaMessage, topicName);
    }



}
