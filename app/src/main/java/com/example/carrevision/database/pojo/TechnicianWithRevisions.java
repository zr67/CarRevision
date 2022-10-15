package com.example.carrevision.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;

import java.util.List;

@Deprecated // use only if login is implemented
public class TechnicianWithRevisions {
    @Embedded
    public TechnicianEntity technician;
    @Relation(parentColumn = "id", entityColumn = "technician_id", entity = RevisionEntity.class)
    public List<RevisionEntity> revisions;
}
