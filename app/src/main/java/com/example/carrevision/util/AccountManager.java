package com.example.carrevision.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carrevision.ui.BaseActivity;

/**
 * Account manager singleton
 */
public class AccountManager {
    private static volatile AccountManager accountManager;
    private static final String PREFS_USER = "LoggedTech", PREFS_ADMIN = "AdminPrivileges";
    private final SharedPreferences preferences;
    private int technicianId;
    private boolean admin;

    /**
     * Account manager constructor
     * @param preferences Application's shared preferences
     */
    private AccountManager(SharedPreferences preferences) {
        this.preferences = preferences;
        technicianId = preferences.getInt(PREFS_USER, -1);
        admin = preferences.getBoolean(PREFS_ADMIN, false);
    }

    /**
     * Account manager instance getter
     * @param context Application's context
     * @return Account manager
     */
    public static AccountManager getInstance(Context context) {
        if (accountManager == null) {
            synchronized (AccountManager.class) {
                if (accountManager == null) {
                    accountManager = new AccountManager(context.getSharedPreferences(BaseActivity.PREFS_NAME, 0));
                }
            }
        }
        return accountManager;
    }

    /**
     * Method used to indicate when a user was logged in
     * @param technicianId Connected technician's identifier
     * @param admin Connected technician's privileges
     * @param remember Indicates whether or not the account should be restored at application restart
     */
    public void login(int technicianId, boolean admin, boolean remember) {
        this.technicianId = technicianId;
        this.admin = admin;
        if (remember) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(PREFS_USER, technicianId);
            editor.putBoolean(PREFS_ADMIN, admin);
            editor.apply();
        }
    }

    /**
     * Method used to indicate when a user logs out
     * Suppresses persistence if existing
     */
    public void logout() {
        this.technicianId = -1;
        this.admin = false;
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(PREFS_USER)) {
            editor.remove(PREFS_USER);
        }
        if (preferences.contains(PREFS_ADMIN)) {
            editor.remove(PREFS_ADMIN);
        }
        editor.apply();
    }

    /**
     * Gets if a technician is connected
     * @return True if connected, false otherwise
     */
    public boolean isTechnicianConnected() {
        return technicianId >= 0;
    }

    /**
     * Gets the connected technician's identifier
     * @return Connected technician's identifier, or -1 if no technician is connected
     */
    public int getConnectedTechnicianId() {
        return technicianId;
    }

    /**
     * Gets if the connected technician is an admin
     * @return True if he's an admin, false if not or no technician is connected
     */
    public boolean isConnectedTechnicianAdmin() {
        return admin;
    }
}