package com.android.stas.weatherstation.View.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.WindowManager;

/**
 * Created by root on 02.07.16.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideStatusBar();
        
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }
    private void hideStatusBar(){

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}