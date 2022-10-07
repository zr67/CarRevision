package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "technicians")
public class TechnicianEntity {
    // Fields
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "name")
    private String firstname;
    @ColumnInfo(name = "surname")
    private String lastname;
    @ColumnInfo(name = "email")
    private String email;

    // Constructors
    public TechnicianEntity() {}
    public TechnicianEntity(String title, @NonNull String firstname, @NonNull String lastname, @NonNull String email) {
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setFirstname(@NonNull String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(@NonNull String lastname) {
        this.lastname = lastname;
    }
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    // Overridden methods
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
