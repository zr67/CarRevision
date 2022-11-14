package com.example.carrevision.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Canton entity class
 */
@Entity(tableName = "cantons")
public class CantonEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "canton")
    private String canton;
    @NonNull
    @ColumnInfo(name = "abbr")
    private final String abbreviation;

    /**
     * Default constructor for the canton entity class
     */
    @Ignore
    public CantonEntity() {
        this.abbreviation = "";
    }

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
}
