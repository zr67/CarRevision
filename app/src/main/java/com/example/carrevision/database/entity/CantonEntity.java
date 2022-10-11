package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cantons")
public class CantonEntity {
    // Fields
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "canton")
    private String canton;
    @ColumnInfo(name = "abbr")
    private String abbreviation;

    // Constructors
    @Ignore
    public CantonEntity() {}
    public CantonEntity(@NonNull String canton, @NonNull String abbreviation) {
        this.canton = canton;
        this.abbreviation = abbreviation;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getCanton() {
        return canton;
    }
    public String getAbbreviation() {
        return abbreviation;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setCanton(@NonNull String canton) {
        this.canton = canton;
    }
    public void setAbbreviation(@NonNull String abbreviation) {
        this.abbreviation = abbreviation;
    }

    // Overridden methods
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof CantonEntity) {
            return obj == this || ((CantonEntity)obj).getCanton().equals(this.getCanton());
        }
        return false;
    }
    @NonNull
    @Override
    public String toString() {
        return getId() + " " + getCanton() + " " + getAbbreviation();
    }
}
