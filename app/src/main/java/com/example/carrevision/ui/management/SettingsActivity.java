package com.example.carrevision.ui.management;

import android.os.Bundle;

import com.example.carrevision.ui.BaseActivity;
import com.example.carrevision.util.LocaleManager;

public class SettingsActivity extends BaseActivity {
    private LocaleManager localeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localeManager = new LocaleManager();

        /*
        EXAMPLE OF USE

        LocaleManager lm = new LocaleManager();
        Context c = lm.setLocale(Settings.this, "fr");
        Resources res = c.getResources();
        setTitle(res.getString(R.string.RESOURCE_NAME));
         */
    }
}
