package com.example.carrevision.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.carrevision.database.entity.CarEntity;
import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;

/**
 * Pojo containing a revision with it's car and it's technician
 */
public class CompleteRevision {
    @Embedded
    public RevisionEntity revision;
    @Relation(parentColumn = "car_id", entityColumn = "id", entity = CarEntity.class)
    public CompleteCar completeCar;
    @Relation(parentColumn = "technician_id", entityColumn = "id", entity = TechnicianEntity.class)
    public TechnicianEntity technician;
}
