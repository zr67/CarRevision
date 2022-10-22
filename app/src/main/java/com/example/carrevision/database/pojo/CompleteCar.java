package com.example.carrevision.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.ModelEntity;

/**
 * Pojo containing the car with it's brand and model
 */
public class CompleteCar {
    @Embedded
    public CarEntity car;
    @Relation(parentColumn = "model_id", entityColumn = "id", entity = ModelEntity.class)
    public ModelWithBrand modelWithBrand;
}
