package com.carrental.microservices.orderservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Extractor helper util class.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Extractor {

    public static UUID extractUUIDFromString(String uuid) {
        return UUID.fromString(uuid);
    }
}
