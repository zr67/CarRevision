package com.example.carrevision.ui.management;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;

import com.example.carrevision.R;
import com.example.carrevision.util.LocaleManager;

import java.util.List;

/**
 * Settings activity class
 */
public class SettingsActivity extends PreferenceActivity {
    /**
     * Helper method to determine if the device has extra-large screen
     * @param context Context
     * @return True if device has extra-large screen
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_lang, false);
        LocaleManager localeManager = new LocaleManager(this);
        localeManager.applyLanguage();
        setupActionBar();
        setActionBarTitle(R.string.action_settings);
    }

    /**
     * Sets up the actionbar
     */
    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Sets the actionbar title
     * @param resource String resource identifier
     */
    public void setActionBarTitle(int resource) {
        setTitle(getString(resource));
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName) || AboutPreferenceFragment.class.getName().equals(fragmentName) || LanguagePreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * About preference fragment class
     */
    public static class AboutPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_about);
            setHasOptionsMenu(true);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Language preference fragment class
     */
    public static class LanguagePreferenceFragment extends PreferenceFragment {
        private static final String LANG = "LANGUAGE";

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_lang);
            setHasOptionsMenu(true);
            Preference langPref = findPreference(LANG);
            langPref.setOnPreferenceChangeListener((preference, o) -> {
                ListPreference listPref = (ListPreference) preference;
                int idx = listPref.findIndexOfValue(o.toString());
                preference.setSummary(idx >= 0 ? listPref.getEntries()[idx] : null);
                // TODO translate page or warning restart needed?
                return true;
            });
            langPref.setSummary(PreferenceManager.getDefaultSharedPreferences(langPref.getContext()).getString(LANG, ""));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
