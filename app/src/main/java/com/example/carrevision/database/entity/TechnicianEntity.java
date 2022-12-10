package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;

/**
 * Technician entity class
 */
public class TechnicianEntity implements Comparable<TechnicianEntity> {
    private String id;
    private String title;
    private String firstname;
    private String lastname;
    private boolean admin;

    /**
     * Default constructor for the technician entity class
     */
    private TechnicianEntity() {}

    /**
     * Constructor for the technician entity class
     * @param title Technician's title
     * @param firstname Technician's first name
     * @param lastname Technician's last name
     */
    public TechnicianEntity(String title, @NonNull String firstname, @NonNull String lastname, boolean admin) {
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.admin = admin;
    }

    /**
     * Gets the technician's identifier
     * @return Technician's identifier
     */
    @Exclude
    public String getId() {
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
     * Gets if the technician is an admin
     * @return True if the technician is an admin, false otherwise
     */
    public boolean getAdmin() {
        return admin;
    }

    /**
     * Sets the technician's identifier
     * @param id Technician's identifier
     */
    public void setId(String id) {
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
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Sets the technician's last name
     * @param lastname Technician's last name
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Sets if the technician is admin
     * @param admin Technician's admin status
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof TechnicianEntity) {
            TechnicianEntity t = (TechnicianEntity) obj;
            return obj == this || (t.getFirstname().equals(this.getFirstname()) && t.getLastname().equals(this.getLastname()));
        }
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return getTitle() + " " + getFirstname() + " " + getLastname();
    }

    @Override
    public int compareTo(TechnicianEntity o) {
        return this.getFirstname().compareTo(o.getFirstname());
    }
}
