package database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "cars")
public class CarEntity {
    // Fields
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "model_id")
    private int modelId;
    @ColumnInfo(name = "plate")
    private String plate;
    @ColumnInfo(name = "year")
    private Date year;
    @ColumnInfo(name = "kilometers")
    private int kilometers;

    // Constructors
    @Ignore
    public CarEntity() {}
    public CarEntity(int modelId, @NonNull String plate, @NonNull Date year, int kilometers) {
        this.modelId = modelId;
        this.plate = plate;
        this.year = year;
        this.kilometers = kilometers;
    }

    // Getters
    public int getId() {
        return id;
    }
    public int getModelId() {
        return modelId;
    }
    public String getPlate() {
        return plate;
    }
    public Date getYear() {
        return year;
    }
    public int getKilometers() {
        return kilometers;
    }

    // Setters
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }
    public void setPlate(@NonNull String plate) {
        this.plate = plate;
    }
    public void setYear(@NonNull Date year) {
        this.year = year;
    }
    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    // Overridden methods
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
        return getId() + " " + getModelId() + " " + getPlate() + " " + getYear() + " " + getKilometers();
    }
}
