package com.example.carrevision.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Locale manager singleton
 */
public class LocaleManager {
    private final String LANG = "Locale.Helper.Selected.Language";
    private String language;
    private static LocaleManager instance;

    /**
     * Locale manager private constructor
     */
    private LocaleManager() {
        language = "en";
    }

    /**
     * Gets the locale manager instance
     * @return Locale manager instance
     */
    public static LocaleManager getInstance() {
        if (instance == null) {
            synchronized (LocaleManager.class) {
                if (instance == null) {
                    instance = new LocaleManager();
                }
            }
        }
        return instance;
    }

    /**
     * Sets the language
     * @param context Context
     * @param language Language
     * @return New context
     */
    public Context setLocale(Context context, String language) {
        setLanguage(context, language);
        this.language = language;
        return updateResources(context, language);
    }

    /**
     * Gets the language
     * @param context Context
     * @return Language
     */
    public String getLanguage(Context context) {
        return language;
    }

    /**
     * Sets the language in the preferences
     * @param context Context
     * @param language Language
     */
    private void setLanguage(Context context, String language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANG, language);
        editor.apply();
    }

    /**
     * Updates the context with the new language
     * @param context Context
     * @param language New language
     * @return Updated context
     */
    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration conf = context.getResources().getConfiguration();
        conf.setLocale(locale);
        conf.setLayoutDirection(locale);
        return context.createConfigurationContext(conf);
    }
}
