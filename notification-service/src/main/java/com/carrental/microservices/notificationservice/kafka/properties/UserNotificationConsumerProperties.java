package com.carrental.microservices.notificationservice.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * UserNotificationProducerProperties class that provides access to the kafka Producer properties.
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka.user-notification.consumer")
public class UserNotificationConsumerProperties {
    
    private String topic;

    private String groupId;
}
