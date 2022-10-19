package com.example.carrevision.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleManager {
    private final String LANG = "Locale.Helper.Selected.Language";

    public Context setLocale(Context context, String language) {
        setLanguage(context, language);
        return updateResources(context, language);
    }

    private void setLanguage(Context context, String language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANG, language);
        editor.apply();
    }
    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration conf = context.getResources().getConfiguration();
        conf.setLocale(locale);
        conf.setLayoutDirection(locale);
        return context.createConfigurationContext(conf);
    }
}
