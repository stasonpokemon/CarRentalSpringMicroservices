package com.carrental.microservices.notificationservice.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

/**
 * MailKafkaMessage kafka message class.
 */
@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class MailKafkaMessage implements Serializable {

    private String emailTo;

    private String subject;

    private String message;
}
