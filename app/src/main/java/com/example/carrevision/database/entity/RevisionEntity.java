package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "revision")
public class RevisionEntity {
    // Fields
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

    // Constructors
    @Ignore
    public RevisionEntity() {}
    public RevisionEntity(int technicianId, int carId, @NonNull Date start, Date end, String status) {
        this.technicianId = technicianId;
        this.carId = carId;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }
    public int getTechnicianId() {
        return technicianId;
    }
    public int getCarId() {
        return carId;
    }
    public Date getStart() {
        return start;
    }
    public Date getEnd() {
        return end;
    }
    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }
    public void setStart(@NonNull Date start) {
        this.start = start;
    }
    public void setEnd(Date end) {
        this.end = end;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // Overridden methods
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
