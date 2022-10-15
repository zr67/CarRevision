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
 * Revision entity class
 */
@Entity(tableName = "revisions",
        foreignKeys = {
                @ForeignKey(
                        entity = TechnicianEntity.class,
                        parentColumns = "id",
                        childColumns = "technician_id"
                ),
                @ForeignKey(
                        entity = CarEntity.class,
                        parentColumns = "id",
                        childColumns = "car_id"
                )},
                indices = {
                @Index(
                        value = {"technician_id"}
                ),
                @Index(
                        value = {"car_id"}
                )}
        )
public class RevisionEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "technician_id")
    private int technicianId;
    @ColumnInfo(name = "car_id")
    private int carId;
    @ColumnInfo(name = "start")
    private Date start;
    @ColumnInfo(name = "end")
    private Date end;
    @ColumnInfo(name = "status")
    private String status;

    /**
     * Default constructor for the revision entity class
     */
    @Ignore
    public RevisionEntity() {}

    /**
     * Constructor for the revision entity class
     * @param technicianId Technician's identifier
     * @param carId Car's identifier
     * @param start Revision's start date and time
     * @param end Revision's end date and time
     * @param status Revision's status
     */
    public RevisionEntity(int technicianId, int carId, @NonNull Date start, Date end, String status) {
        this.technicianId = technicianId;
        this.carId = carId;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    /**
     * Gets the revision's identifier
     * @return Revision's identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the technician's identifier
     * @return Technician's identifier
     */
    public int getTechnicianId() {
        return technicianId;
    }

    /**
     * Gets the car's identifier
     * @return Car's identifier
     */
    public int getCarId() {
        return carId;
    }

    /**
     * Gets the revision's start date and time
     * @return Revision's start date and time
     */
    public Date getStart() {
        return start;
    }

    /**
     * Gets the revision's end date and time
     * @return Revision's end date and time
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Gets the revision's status
     * @return Revision's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the revision's identifier
     * @param id Revision's identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the technician's identifier
     * @param technicianId Technician's identifier
     */
    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }

    /**
     * Sets the car's identifier
     * @param carId Car's identifier
     */
    public void setCarId(int carId) {
        this.carId = carId;
    }

    /**
     * Sets the revision's start date and time
     * @param start Revision's start date and time
     */
    public void setStart(@NonNull Date start) {
        this.start = start;
    }

    /**
     * Sets the revision's end date and time
     * @param end Revision's end date and time
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Sets the revision's status
     * @param status Revision's status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof RevisionEntity) {
            RevisionEntity r = (RevisionEntity) obj;
            return obj == this || (r.getCarId() == this.getCarId() && r.getStart().equals(this.getStart()));
        }
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return getId() + " " + getTechnicianId() + " " + getCarId() + " " + getStart() + " " + getEnd() + " " + getStatus();
    }
}
