package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "brands")
public class BrandEntity {
    // Fields
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "brand")
    private String brand;

    // Constructors
    @Ignore
    public BrandEntity() {
        this.brand = "";
    }
    public BrandEntity(@NonNull String brand) {
        this.brand = brand;
    }

    // Getters
    public int getId() {
        return id;
    }
    @NonNull
    public String getBrand() {
        return brand;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setBrand(@NonNull String brand) {
        this.brand = brand;
    }

    // Overridden methods
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof BrandEntity) {
            return obj == this || ((BrandEntity)obj).getBrand().equals(this.getBrand());
        }
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return getId() + " " + getBrand();
    }
}