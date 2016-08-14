package com.android.stas.weatherstation.View.Settings;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by root on 02.07.16.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }
}