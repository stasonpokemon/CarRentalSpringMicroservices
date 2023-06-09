package com.carrental.microservices.userservice.kafka.message;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class MailKafkaMessage {

    private String emailTo;

    private String subject;

    private String message;
}
