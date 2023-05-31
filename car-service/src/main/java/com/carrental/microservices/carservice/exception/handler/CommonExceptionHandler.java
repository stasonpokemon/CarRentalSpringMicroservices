package com.carrental.microservices.carservice.exception.handler;

import com.carrental.microservices.carservice.exception.BadRequestException;
import com.carrental.microservices.carservice.exception.NotFoundException;
import com.carrental.microservices.carservice.exception.dto.ErrorTypeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The CommonExceptionHandler class for handling exceptions.
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> notFoundExceptionHandler(
            NotFoundException notFoundException) {

        log.info("Caught NotFoundException: {}", notFoundException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(notFoundException.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> badRequestExceptionHandler(
            BadRequestException badRequestException) {

        log.info("Caught BadRequestException: {}", badRequestException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(badRequestException.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException methodArgumentNotValidException) {

        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        log.info("Caught MethodArgumentNotValidException: {}", errors);

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(errors).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> methodArgumentNotValidExceptionHandler(
            MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {

        log.info("Caught MethodArgumentTypeMismatchException: {}", methodArgumentTypeMismatchException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(methodArgumentTypeMismatchException.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> propertyReferenceExceptionHandler(
            PropertyReferenceException propertyReferenceException) {

        log.info("Caught PropertyReferenceException: {}", propertyReferenceException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(propertyReferenceException.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

}
