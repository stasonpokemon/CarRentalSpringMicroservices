package com.carrental.microservices.carservice.domain.entity;

import java.util.stream.Stream;

/**
 * CarStatus enum class.
 */
public enum CarStatus {

    FREE(0), BUSY(1), BROKEN(2), DELETED(3);


    final int number;

    CarStatus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public static CarStatus of(int number) {
        return Stream.of(CarStatus.values())
                .filter(p -> p.getNumber() == number)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
