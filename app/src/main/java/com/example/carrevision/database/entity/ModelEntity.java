package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Model entity class
 */
@Entity(tableName = "models",
        foreignKeys =
        @ForeignKey(
                entity = BrandEntity.class,
                parentColumns = "id",
                childColumns = "brand_id"
        ),
        indices = {
        @Index(
                value = {"brand_id"}
        )}
)
public class ModelEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "brand_id")
    private int brandId;
    @NonNull
    @ColumnInfo(name = "model")
    private final String model;

    /**
     * Default constructor for the model entity class
     */
    @Ignore
    public ModelEntity() {
        this.model = "";
    }

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
}
