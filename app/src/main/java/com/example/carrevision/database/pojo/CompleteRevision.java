package com.example.carrevision.database.pojo;

import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.entity.TechnicianEntity;

/**
 * Pojo containing a revision with it's car and it's technician
 */
public class CompleteRevision implements Comparable<CompleteRevision> {
    public RevisionEntity revision;
    public CompleteCar completeCar;
    public TechnicianEntity technician;

    @Override
    public int compareTo(CompleteRevision o) {
        return this.revision.compareTo(o.revision);
    }
}
