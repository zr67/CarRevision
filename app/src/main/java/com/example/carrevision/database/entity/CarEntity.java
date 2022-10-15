package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Car entity class
 */
@Entity(tableName = "cars",
        foreignKeys =
        @ForeignKey(
                entity = ModelEntity.class,
                parentColumns = "id",
                childColumns = "model_id"
        ),
        indices = {
        @Index(
                value = {"model_id"}
        )}
)
public class CarEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "model_id")
    private int modelId;
    @NonNull
    @ColumnInfo(name = "plate")
    private String plate;
    @ColumnInfo(name = "year")
    private Date year;
    @ColumnInfo(name = "kilometers")
    private int kilometers;

    /**
     * Default constructor for the car entity class
     */
    @Ignore
    public CarEntity() {
        this.plate = "";
    }

    /**
     * Constructor for the car entity class
     * @param modelId Car's model identifier
     * @param plate Car's plate number
     * @param year Car's issuance year
     * @param kilometers Car's mileage
     */
    public CarEntity(int modelId, @NonNull String plate, @NonNull Date year, int kilometers) {
        this.modelId = modelId;
        this.plate = plate;
        this.year = year;
        this.kilometers = kilometers;
    }

    /**
     * Gets the car's identifier
     * @return Car's identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the car's model identifier
     * @return Car's model identifier
     */
    public int getModelId() {
        return modelId;
    }

    /**
     * Gets the car's plate number
     * @return Car's plate number
     */
    @NonNull
    public String getPlate() {
        return plate;
    }

    /**
     * Gets the car's issuance year
     * @return Car's issuance year
     */
    public Date getYear() {
        return year;
    }

    /**
     * Gets the car's mileage
     * @return Car's mileage
     */
    public int getKilometers() {
        return kilometers;
    }

    /**
     * Sets the car's identifier
     * @param id Car's identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the car's model identifier
     * @param modelId Car's model identifier
     */
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    /**
     * Sets the car's plate number
     * @param plate Car's plate number
     */
    public void setPlate(@NonNull String plate) {
        this.plate = plate;
    }

    /**
     * Sets the car's issuance year
     * @param year Car's issuance year
     */
    public void setYear(@NonNull Date year) {
        this.year = year;
    }

    /**
     * Sets the car's mileage
     * @param kilometers Car's mileage
     */
    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof CarEntity) {
            CarEntity c = (CarEntity) obj;
            return obj == this || (c.getPlate().equals(this.getPlate()) && c.getModelId() == this.getModelId());
        }
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return getId() + " " + getModelId() + " " + getPlate() + " " + getYear() + " " + getKilometers();
    }
}
