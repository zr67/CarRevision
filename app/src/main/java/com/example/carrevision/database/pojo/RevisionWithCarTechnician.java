package com.example.carrevision.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;

/**
 * Pojo containing a revision with the linked car and technician
 */
public class RevisionWithCarTechnician {
    @Embedded
    public RevisionEntity revision;
    @Relation(parentColumn = "car_id", entityColumn = "id", entity = CarEntity.class)
    public CarEntity car;
    @Relation(parentColumn = "technician_id", entityColumn = "id", entity = TechnicianEntity.class)
    public TechnicianEntity technician;
}
