package com.carrental.microservices.carservice.exception;

/**
 * Exception for BadRequest actions.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
