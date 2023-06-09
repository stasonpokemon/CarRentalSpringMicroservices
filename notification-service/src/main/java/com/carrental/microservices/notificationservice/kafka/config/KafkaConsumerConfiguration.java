package com.carrental.microservices.notificationservice.kafka.config;

import com.carrental.microservices.notificationservice.kafka.message.MailKafkaMessage;
import com.carrental.microservices.notificationservice.kafka.properties.UserNotificationConsumerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaConsumerConfiguration configuration class.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final UserNotificationConsumerProperties userNotificationConsumerProperties;

    @Bean(name = "userNotificationConsumerConfig")
    public Map<String, Object> consumerConfig() {
        log.info("Bootstrap server: {}",bootstrapServers);
        log.info("groupId: {}",userNotificationConsumerProperties.getGroupId());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, userNotificationConsumerProperties.getGroupId());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return properties;
    }

    @Bean(name = "userNotificationConsumerFactory")
    public ConsumerFactory<String, MailKafkaMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(MailKafkaMessage.class));
    }

    @Bean(name = "userNotificationConsumerContainerFactory")
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, MailKafkaMessage>> listenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MailKafkaMessage> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
