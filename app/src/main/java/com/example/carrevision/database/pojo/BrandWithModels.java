package com.example.carrevision.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.carrevision.database.entity.BrandEntity;
import com.example.carrevision.database.entity.ModelEntity;

import java.util.List;

public class BrandWithModels {
    @Embedded
    public BrandEntity brand;
    @Relation(parentColumn = "id", entityColumn = "brand_id", entity = ModelEntity.class)
    public List<ModelEntity> models;
}
