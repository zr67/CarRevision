package com.example.carrevision.database.repository;

import androidx.lifecycle.LiveData;

import com.example.carrevision.database.entity.TechnicianEntity;
import com.example.carrevision.database.firebase.TechnicianListLiveData;
import com.example.carrevision.database.firebase.TechnicianLiveData;
import com.example.carrevision.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Technician repository singleton
 */
public class TechnicianRepository {
    private static volatile TechnicianRepository instance;
    public static final String TABLE = "technicians";

    /**
     * Technician repository class private constructor
     */
    private TechnicianRepository() {}

    /**
     * Gets the technician repository instance
     * @return Technician repository instance
     */
    public static TechnicianRepository getInstance() {
        if (instance == null) {
            synchronized (TechnicianRepository.class) {
                if (instance == null) {
                    instance = new TechnicianRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Gets all the technicians from the database
     * @return List with all the technicians
     */
    public LiveData<List<TechnicianEntity>> getTechnicians() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(TABLE);
        return new TechnicianListLiveData(reference);
    }

    /**
     * Logs in a technician
     * @param email Technician's email
     * @param password Technician's password
     * @param listener Listener
     */
    public void login(final String email, final String password, final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    /**
     * Gets a technician by it's identifier
     * @param id Technician's uid
     * @return Technician corresponding to the email
     */
    public LiveData<TechnicianEntity> getTechnician(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(TABLE);
        return new TechnicianLiveData(reference, id);
    }

    /**
     * Registers and create a new technician
     * @param technician Technician to be registered
     * @param email Technician's email
     * @param password Technician's password
     * @param callback Callback
     */
    public void register(final TechnicianEntity technician, final String email, final String password, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        technician.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        create(technician, callback);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    /**
     * Creates a new technician
     * @param technician Technician to be created
     * @param callback Callback
     */
    private void create(final TechnicianEntity technician, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference(TABLE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(technician, (dbErr, dbRef) -> {
                    if (dbErr != null) {
                        callback.onFailure(dbErr.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
