package com.carrental.microservices.notificationservice.service;

/**
 * The MailSenderService interface, which stores the business logic for sending emails to the user.
 */
public interface MailSenderService {

    void send(String emailTo, String subject, String message);
}
