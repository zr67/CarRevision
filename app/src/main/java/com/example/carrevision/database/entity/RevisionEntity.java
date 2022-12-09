package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carrevision.database.Converters;
import com.example.carrevision.util.Status;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Revision entity class
 */
public class RevisionEntity implements Comparable<RevisionEntity> {
    private String id;
    private String technicianId;
    private String carId;
    private Date start;
    private Date end;
    private Status status;

    /**
     * Default constructor for the revision entity class
     */
    private RevisionEntity() {}

    /**
     * Constructor for the revision entity class
     * @param technicianId Technician's identifier
     * @param carId Car's identifier
     * @param start Revision's start date and time
     * @param end Revision's end date and time
     * @param status Revision's status
     */
    public RevisionEntity(String technicianId, String carId, @NonNull Date start, Date end, Status status) {
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
    @Exclude
    public String getId() {
        return id;
    }

    /**
     * Gets the technician's identifier
     * @return Technician's identifier
     */
    public String getTechnicianId() {
        return technicianId;
    }

    /**
     * Gets the car's identifier
     * @return Car's identifier
     */
    public String getCarId() {
        return carId;
    }

    /**
     * Gets the revision's start date and time
     * @return Revision's start date and time in long
     */
    public Long getStart() {
        return Converters.dateToTimestamp(start);
    }

    /**
     * Gets the revision's start date and time
     * @return Revision's start date and time
     */
    @Exclude
    public Date getStartDate() {
        return start;
    }

    /**
     * Gets the revision's end date and time
     * @return Revision's end date and time in long
     */
    public Long getEnd() {
        return Converters.dateToTimestamp(end);
    }

    /**
     * Gets the revision's end date and time
     * @return Revision's end date and time
     */
    @Exclude
    public Date getEndDate() {
        return end;
    }

    /**
     * Gets the revision's status
     * @return Revision's status's integer value
     */
    public int getStatus() {
        return Converters.statusToInt(status);
    }

    /**
     * Gets the revision's status
     * @return Revision's status
     */
    @Exclude
    public Status getEStatus() {
        return status;
    }

    /**
     * Sets the revision's identifier
     * @param id Revision's identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the technician's identifier
     * @param technicianId Technician's identifier
     */
    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    /**
     * Sets the car's identifier
     * @param carId Car's identifier
     */
    public void setCarId(String carId) {
        this.carId = carId;
    }

    /**
     * Sets the revision's start date and time
     * @param start Revision's end date and time
     */
    public void setStart(Long start) {
        this.start = Converters.fromTimestamp(start);
    }

    /**
     * Sets the revision's start date and time
     * @param start Revision's start date and time
     */
    public void setStartDate(@NonNull Date start) {
        this.start = start;
    }

    /**
     * Sets the revision's end date and time
     * @param end End long value
     */
    public void setEnd(Long end) {
        this.end = Converters.fromTimestamp(end);
    }

    /**
     * Sets the revision's end date and time
     * @param end Revision's end date and time
     */
    public void setEndDate(Date end) {
        this.end = end;
    }

    /**
     * Sets the revision's status
     * @param status Status' integer value
     */
    public void setStatus(int status) {
        this.status = Converters.fromInt(status);
    }

    /**
     * Sets the revision's status
     * @param status Revision's status
     */
    @Exclude
    public void setEStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof RevisionEntity) {
            RevisionEntity r = (RevisionEntity) obj;
            return obj == this || (r.getCarId().equals(this.getCarId()) && r.getStartDate().equals(this.getStartDate()));
        }
        return false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> rv = new HashMap<>();
        rv.put("technicianId", technicianId);
        rv.put("carId", carId);
        rv.put("start", Converters.dateToTimestamp(start));
        rv.put("end", Converters.dateToTimestamp(end));
        rv.put("status", Converters.statusToInt(status));
        return rv;
    }

    @Override
    public int compareTo(RevisionEntity o) {
        return this.getStartDate().compareTo(o.getStartDate());
    }
}
