package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;

/**
 * Canton entity class
 */
public class CantonEntity implements Comparable<CantonEntity> {
    private int id;
    private String canton;
    private String abbreviation;

    /**
     * Default constructor for the canton entity class
     */
    private CantonEntity() {}

    /**
     * Constructor for the canton entity class
     * @param canton Canton's name
     * @param abbreviation Canton's abbreviation
     */
    public CantonEntity(@NonNull String canton, @NonNull String abbreviation) {
        this.canton = canton;
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the canton's identifier
     * @return Canton's identifier
     */
    @Exclude
    public int getId() {
        return id;
    }

    /**
     * Gets the canton's name
     * @return Canton's name
     */
    public String getCanton() {
        return canton;
    }

    /**
     * Gets the canton's abbreviation
     * @return Canton's abbreviation
     */
    @NonNull
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets the canton's identifier
     * @param id Canton's identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the canton's name
     * @param canton Canton's name
     */
    public void setCanton(String canton) {
        this.canton = canton;
    }

    /**
     * Sets the canton's abbreviation
     * @param abbreviation Canton's abbreviation
     */
    public void setAbbreviation(@NonNull String abbreviation) {
        this.abbreviation = abbreviation;
    }

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
        return getAbbreviation();
    }

    @Override
    public int compareTo(CantonEntity o) {
        return this.getAbbreviation().compareTo(o.getAbbreviation());
    }
}
