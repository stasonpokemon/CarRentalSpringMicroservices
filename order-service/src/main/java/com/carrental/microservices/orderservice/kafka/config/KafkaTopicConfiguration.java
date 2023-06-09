package com.carrental.microservices.orderservice.kafka.config;

import com.carrental.microservices.orderservice.kafka.properties.OrderCarStatusKafkaProducerProperties;
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

    private final OrderCarStatusKafkaProducerProperties orderCarStatusKafkaProducerProperties;

    @Bean
    public NewTopic orderCarStatusTopic(){
        return TopicBuilder.name(orderCarStatusKafkaProducerProperties.getTopic()).build();
    }
}
