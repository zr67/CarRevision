package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;

/**
 * Brand entity class
 */
public class BrandEntity implements Comparable<BrandEntity> {
    private int id;
    private String brand;

    /**
     * Default constructor for the brand entity class
     */
    private BrandEntity() {}

    /**
     * Constructor for the brand entity class
     * @param brand Brand's name
     */
    public BrandEntity(@NonNull String brand) {
        this.brand = brand;
    }

    /**
     * Gets the brand's identifier
     * @return Brand's identifier
     */
    @Exclude
    public int getId() {
        return id;
    }

    /**
     * Gets the brand's name
     * @return Brand's name
     */
    @NonNull
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand's identifier
     * @param id Brand's identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the brand's name
     * @param brand Brand's name
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

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
        return getBrand();
    }

    @Override
    public int compareTo(BrandEntity o) {
        return this.getBrand().compareTo(o.getBrand());
    }
}
