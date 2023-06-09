package com.carrental.microservices.userservice.service;


import com.carrental.microservices.userservice.domain.entity.User;

public interface MailService {
    void sendActivationCode(User user);
}
