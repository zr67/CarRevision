package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carrevision.database.Converters;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Car entity class
 */
public class CarEntity implements Comparable<CarEntity> {
    private String id;
    private int modelId;
    private String plate;
    private Date year;
    private int kilometers;

    /**
     * Default constructor for the car entity class
     */
    private CarEntity() {
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

    public CarEntity(int modelId, @NonNull String plate, @NonNull Long year, int kilometers) {
        this.modelId = modelId;
        this.plate = plate;
        this.year = Converters.fromTimestamp(year);
        this.kilometers = kilometers;
    }

    /**
     * Gets the car's identifier
     * @return Car's identifier
     */
    @Exclude
    public String getId() {
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
     * @return Car's issuance year in long
     */
    public Long getYear() {
        return Converters.dateToTimestamp(year);
    }

    /**
     * Gets the car's issuance year
     * @return Car's issuance year
     */
    @Exclude
    public Date getYearDate() {
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
    public void setId(String id) {
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
     * @param year Car's issuance year in long
     */
    public void setYear(Long year) {
        this.year = Converters.fromTimestamp(year);
    }

    /**
     * Sets the car's issuance year
     * @param year Car's issuance year
     */
    public void setYearDate(@NonNull Date year) {
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
        return getId() + " " + getModelId() + " " + getPlate() + " " + getYearDate() + " " + getKilometers();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> rv = new HashMap<>();
        rv.put("modelId", modelId);
        rv.put("plate", plate);
        rv.put("year", Converters.dateToTimestamp(year));
        rv.put("kilometers", kilometers);
        return rv;
    }

    @Override
    public int compareTo(CarEntity o) {
        return this.getPlate().compareTo(o.getPlate());
    }
}
