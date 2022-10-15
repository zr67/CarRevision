package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Technician entity class
 */
@Entity(tableName = "technicians")
public class TechnicianEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "name")
    private String firstname;
    @ColumnInfo(name = "surname")
    private String lastname;
    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    /**
     * Default constructor for the technician entity class
     */
    @Ignore
    public TechnicianEntity() {
        this.email = "";
    }

    /**
     * Constructor for the technician entity class
     * @param title Technician's title
     * @param firstname Technician's first name
     * @param lastname Technician's last name
     * @param email Technician's email
     */
    public TechnicianEntity(String title, @NonNull String firstname, @NonNull String lastname, @NonNull String email) {
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    /**
     * Gets the technician's identifier
     * @return Technician's identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the technician's title
     * @return Technician's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the technician's first name
     * @return Technician's first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Gets the technician's last name
     * @return Technician's last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Gets the technician's email
     * @return Technician's email
     */
    @NonNull
    public String getEmail() {
        return email;
    }

    /**
     * Sets the technician's identifier
     * @param id Technician's identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the technician's title
     * @param title Technician's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the technician's first name
     * @param firstname Technician's first name
     */
    public void setFirstname(@NonNull String firstname) {
        this.firstname = firstname;
    }

    /**
     * Sets the technician's last name
     * @param lastname Technician's last name
     */
    public void setLastname(@NonNull String lastname) {
        this.lastname = lastname;
    }

    /**
     * Sets the technician's email
     * @param email Technician's email
     */
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof TechnicianEntity) {
            TechnicianEntity t = (TechnicianEntity) obj;
            return obj == this || (t.getFirstname().equals(this.getFirstname()) && t.getLastname().equals(this.getLastname()) && t.getEmail().equals(this.getEmail()));
        }
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return getId() + " " + getTitle() + " " + getFirstname() + " " + getLastname() + " " + getEmail();
    }
}
