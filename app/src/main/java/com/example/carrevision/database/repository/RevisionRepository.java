package com.example.carrevision.database.repository;

import androidx.lifecycle.LiveData;

import com.example.carrevision.database.entity.RevisionEntity;
import com.example.carrevision.database.firebase.RevisionLightListLiveData;
import com.example.carrevision.database.firebase.RevisionListLiveData;
import com.example.carrevision.database.firebase.RevisionLiveData;
import com.example.carrevision.database.pojo.CompleteRevision;
import com.example.carrevision.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Revision repository singleton
 */
public class RevisionRepository {
    private static volatile RevisionRepository instance;
    public static final String TABLE = "revisions";

    /**
     * Revision repository private constructor
     */
    private RevisionRepository() {}

    /**
     * Gets the revision repository instance
     * @return Revision repository instance
     */
    public static RevisionRepository getInstance() {
        if (instance == null) {
            synchronized (RevisionRepository.class) {
                if (instance == null) {
                    instance = new RevisionRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets all revisions from the database
     * @return List with all the revisions
     */
    public LiveData<List<CompleteRevision>> getRevisions() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return new RevisionListLiveData(reference);
    }

    /**
     * Gets all revisions that have the car linked to it
     * @param carId Car's id
     * @return List with all linked revisions
     */
    public LiveData<List<RevisionEntity>> getRevisionsLight(String carId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(TABLE);
        return new RevisionLightListLiveData(reference, carId);
    }

    /**
     * Gets a revision by it's identifier
     * @param revisionId Revision's identifier
     * @return Revision with all it's associated objects
     */
    public LiveData<CompleteRevision> getRevision(String revisionId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return new RevisionLiveData(reference, revisionId);
    }

    /**
     * Creates a new revision
     * @param revision Revision to create
     * @param callback Callback
     */
    public void create(final RevisionEntity revision, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference(TABLE)
                .push()
                .setValue(revision, (dbErr, dbRef) -> {
                    if (dbErr != null) {
                        callback.onFailure(dbErr.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * Updates a revision
     * @param revision Revision to update
     * @param callback Callback
     */
    public void update(final RevisionEntity revision, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference(TABLE)
                .child(revision.getId())
                .updateChildren(revision.toMap(), (dbErr, dbRef) -> {
                    if (dbErr != null) {
                        callback.onFailure(dbErr.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /**
     * Deletes a revision
     * @param revision Revision to delete
     * @param callback Callback
     */
    public void delete(final RevisionEntity revision, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference(TABLE)
                .child(revision.getId())
                .removeValue((dbErr, dbRef) -> {
                    if (dbErr != null) {
                        callback.onFailure(dbErr.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
