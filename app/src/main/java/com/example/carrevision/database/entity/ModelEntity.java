package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;

/**
 * Model entity class
 */
public class ModelEntity implements Comparable<ModelEntity> {
    private int id;
    private int brandId;
    private String model;

    /**
     * Default constructor for the model entity class
     */
    private ModelEntity() {}

    /**
     * Constructor for the model entity class
     * @param brandId Brand's identifier
     * @param model Model's name
     */
    public ModelEntity(int brandId, @NonNull String model) {
        this.brandId = brandId;
        this.model = model;
    }

    /**
     * Gets the model's identifier
     * @return Model's identifier
     */
    @Exclude
    public int getId() {
        return id;
    }

    /**
     * Gets the brand's identifier
     * @return Brand's identifier
     */
    public int getBrandId() {
        return brandId;
    }

    /**
     * Gets the model's name
     * @return Model's name
     */
    @NonNull
    public String getModel() {
        return model;
    }

    /**
     * Sets the model identifier
     * @param id Model identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the model's brand's identifier
     * @param brandId Model's brand's identifier
     */
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    /**
     * Sets the model's name
     * @param model Model's name
     */
    public void setModel(@NonNull String model) {
        this.model = model;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof ModelEntity) {
            return obj == this || (((ModelEntity)obj).getModel().equals(this.getModel()) && ((ModelEntity)obj).getBrandId() == this.getBrandId());
        }
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return getModel();
    }

    @Override
    public int compareTo(ModelEntity o) {
        return this.getModel().compareTo(o.getModel());
    }
}
