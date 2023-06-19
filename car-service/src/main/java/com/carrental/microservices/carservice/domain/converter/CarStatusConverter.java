package com.carrental.microservices.carservice.domain.converter;

import com.carrental.microservices.carservice.domain.entity.CarStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * CarStatusConverter class for convert CarStatus enum to integer for database.
 */
@Converter(autoApply = true)
public class CarStatusConverter implements AttributeConverter<CarStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CarStatus carStatus) {
        return carStatus == null ? -1 : carStatus.getNumber();
    }

    @Override
    public CarStatus convertToEntityAttribute(Integer integer) {
        return CarStatus.of(integer);
    }
}
