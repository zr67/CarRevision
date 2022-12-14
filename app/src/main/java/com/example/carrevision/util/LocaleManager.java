package com.example.carrevision.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Locale manager class to manage application language and formats
 */
public class LocaleManager {
    public final static String LANG_FR = "FR";
    public final static String LANG_EN = "EN";
    private final String PREFS_LANG = "LANGUAGE";
    private final Activity activity;
    private final SharedPreferences sharedPrefs;

    /**
     * Locale manager class constructor
     * @param activity Activity
     */
    public LocaleManager(Activity activity) {
        this.activity = activity;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        if (!sharedPrefs.contains(PREFS_LANG)) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(PREFS_LANG, LANG_EN);
            editor.apply();
        }
    }

    /**
     * Gets the current language
     * @return Current language
     */
    public String getLanguage() {
        return sharedPrefs.getString(PREFS_LANG, null);
    }

    /**
     * Applies the language stored in the preferences
     */
    @SuppressLint("AppBundleLocaleChanges")
    public void applyLanguage() {
        Locale locale = new Locale(sharedPrefs.getString(PREFS_LANG, null));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
    }
}
