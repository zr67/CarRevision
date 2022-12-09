package com.example.carrevision.database.pojo;

import com.example.carrevision.database.entity.CarEntity;

/**
 * Pojo containing the car with it's brand and model
 */
public class CompleteCar implements Comparable<CompleteCar> {
    public CarEntity car;
    public ModelWithBrand modelWithBrand;

    @Override
    public int compareTo(CompleteCar o) {
        return car.compareTo(o.car);
    }
}
