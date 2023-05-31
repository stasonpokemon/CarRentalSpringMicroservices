package com.carrental.microservices.userservice.service;

/**
 * The MailSenderService interface, which stores the business logic for sending emails to the user.
 */
public interface MailSenderService {

    void send(String emailTo, String subject, String message);
}
