package com.carrental.microservices.notificationservice.kafka.consumer;

import com.carrental.microservices.notificationservice.kafka.message.MailKafkaMessage;
import com.carrental.microservices.notificationservice.kafka.properties.UserNotificationConsumerProperties;
import com.carrental.microservices.notificationservice.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * MailSenderKafkaConsumer class that listens for messages from the producer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderKafkaConsumer {

    private final UserNotificationConsumerProperties userNotificationProducerProperties;

    private final MailSenderService mailSenderService;


    @KafkaListener(
            topics = "${spring.kafka.user-notification.consumer.topic}",
            groupId = "${spring.kafka.user-notification.consumer.groupId}",
            containerFactory = "userNotificationConsumerContainerFactory"
    )
    public void listen(MailKafkaMessage mailKafkaMessage) {
        log.info("Listen message: {} from topic: {}", mailKafkaMessage,
                userNotificationProducerProperties.getTopic());

        mailSenderService.send(mailKafkaMessage.getEmailTo(),
                mailKafkaMessage.getSubject(),
                mailKafkaMessage.getMessage());
    }
}
