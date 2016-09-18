package com.android.stas.weatherstation.View.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.android.stas.weatherstation.Interactor.ServiceManager;
import com.android.stas.weatherstation.Model.Constants;
import com.android.stas.weatherstation.R;

/**
 * @author Stas Zamana on 02.07.16.
 */
public class SettingsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    private ServiceManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        manager = new ServiceManager(getActivity());

        updateSummaryForIP();
        updateSummaryForFrequency();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key)
    {
        if (key.equals(Constants.KEY_PREF_SERVICE) ) {
            CheckBoxPreference servicePref = (CheckBoxPreference)findPreference(key);

            if(servicePref.isChecked()){
                manager.launchService();
            }
            else{
                manager.stopService();
            }

        }
        else if(key.equals(Constants.KEY_PREF_FREQUENCY)){
            CheckBoxPreference servicePref = (CheckBoxPreference)findPreference(Constants.KEY_PREF_SERVICE);

            if(servicePref.isChecked()){
                manager.stopService();
                manager.launchService();
            }
            updateSummaryForFrequency();
        }
        else if (key.equals(Constants.KEY_PREF_IP) ) {
            updateSummaryForIP();
        }
    }

    private void updateSummaryForIP(){

        EditTextPreference ipPref = (EditTextPreference)findPreference(Constants.KEY_PREF_IP);

        String ipText = getSharedPreference().getString(Constants.KEY_PREF_IP, "0");
        ipPref.setSummary(ipText);
    }

    private void updateSummaryForFrequency(){

        EditTextPreference freqPref = (EditTextPreference)findPreference(Constants.KEY_PREF_FREQUENCY);

        String frequencyText = getSharedPreference().getString(Constants.KEY_PREF_FREQUENCY, "5");
        int frequency = Integer.parseInt(frequencyText);
        freqPref.setSummary(frequency + " minutes");
    }

    private SharedPreferences getSharedPreference(){
        return PreferenceManager.getDefaultSharedPreferences(getActivity());
    }
}
