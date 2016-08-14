package com.android.stas.weatherstation.View.Settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.android.stas.weatherstation.R;

/**
 * Created by root on 02.07.16.
 */
public class SettingsFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
