package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
    // Fields
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "brand_id")
    private int brandId;
    @NonNull
    @ColumnInfo(name = "model")
    private String model;

    // Constructors
    @Ignore
    public ModelEntity() {
        this.model = "";
    }
    public ModelEntity(int brandId, @NonNull String model) {
        this.brandId = brandId;
        this.model = model;
    }

    // Getters
    public int getId() {
        return id;
    }
    public int getBrandId() {
        return brandId;
    }
    @NonNull
    public String getModel() {
        return model;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
    public void setModel(@NonNull String model) {
        this.model = model;
    }

    // Overridden methods
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
        return getId() + " " + getBrandId() + " " + getModel();
    }
}
