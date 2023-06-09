package com.carrental.microservices.userservice.kafka.config;

import com.carrental.microservices.userservice.kafka.properties.UserNotificationProducerProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * KafkaTopicConfiguration configuration class.
 */
@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfiguration {

    private final UserNotificationProducerProperties userNotificationProducerProperties;

    @Bean
    public NewTopic userNotificationTopic() {
        return TopicBuilder.name(userNotificationProducerProperties.getTopic()).build();
    }
}
