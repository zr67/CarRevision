package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Brand entity class
 */
@Entity(tableName = "brands")
public class BrandEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "brand")
    private final String brand;

    /**
     * Default constructor for the brand entity class
     */
    @Ignore
    public BrandEntity() {
        this.brand = "";
    }

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
}
