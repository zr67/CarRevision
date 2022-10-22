package com.example.carrevision.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.ModelEntity;

/**
 * Pojo containing the model with it's brand
 */
public class ModelWithBrand {
    @Embedded
    public ModelEntity model;
    @Relation(parentColumn = "brand_id", entityColumn = "id", entity = BrandEntity.class)
    public BrandEntity brand;
}
