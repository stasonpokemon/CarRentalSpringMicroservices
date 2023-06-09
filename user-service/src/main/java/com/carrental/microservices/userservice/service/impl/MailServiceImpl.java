package com.carrental.microservices.userservice.service.impl;

import com.carrental.microservices.userservice.domain.entity.User;
import com.carrental.microservices.userservice.kafka.message.MailKafkaMessage;
import com.carrental.microservices.userservice.kafka.producer.MailSenderKafkaProducer;
import com.carrental.microservices.userservice.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementation class for MailService.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Value("${server.port}")
    private String serverPort;

    private final MailSenderKafkaProducer mailSenderKafkaProducer;

    @Override
    public void sendActivationCode(User user) {

        log.info("Trying to send activation code to user: {}", user);

        String message = String.format("Hello, %s! \n Welcome to car rental website. Please, visit next link " +
                        "for activate your profile: http://localhost:%s/registration/activate/%s!",
                user.getUsername(),
                serverPort,
                user.getActivationCode()
        );

        MailKafkaMessage activationCodeKafkaMessage = MailKafkaMessage.builder()
                .emailTo(user.getEmail())
                .subject("Activation code")
                .message(message).build();

        mailSenderKafkaProducer.sendMessage(activationCodeKafkaMessage);
    }
}
