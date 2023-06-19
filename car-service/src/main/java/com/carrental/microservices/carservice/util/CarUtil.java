package com.carrental.microservices.carservice.util;


import com.carrental.microservices.carservice.domain.entity.Car;
import com.carrental.microservices.carservice.domain.dto.request.UpdateCarRequestDTO;

/**
 * CarUtil class.
 */
public class CarUtil {

    private static CarUtil instance;

    private CarUtil() {
    }

    public static synchronized  CarUtil getInstance() {
        if (instance == null) {
            instance = new CarUtil();
        }
        return instance;
    }


    public void copyNotNullFieldsFromUpdateCarDTOToCar(UpdateCarRequestDTO from, Car to) {
        if (from.getModel() != null && !from.getModel().isEmpty()) {
            to.setModel(from.getModel());
        }
        if (from.getProducer() != null && !from.getProducer().isEmpty()) {
            to.setProducer(from.getProducer());
        }
        if (from.getPricePerDay() != null) {
            to.setPricePerDay(from.getPricePerDay());
        }
        if (from.getReleaseDate() != null) {
            to.setReleaseDate(from.getReleaseDate());
        }
        if (from.getImageLink() != null && !from.getImageLink().isEmpty()) {
            to.setImageLink(from.getImageLink());
        }
        if (from.getDamageStatus() != null && !from.getDamageStatus().isEmpty()) {
            to.setDamageStatus(from.getDamageStatus());
        }
        if (from.getCarStatus() != null){
            to.setCarStatus(from.getCarStatus());
        }
    }

}
