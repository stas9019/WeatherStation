package com.android.stas.weatherstation.View.Settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;

import com.android.stas.weatherstation.Interactor.WeatherStationService;
import com.android.stas.weatherstation.R;

import java.util.Calendar;

/**
 * @author Stas Zamana on 02.07.16.
 */
public class SettingsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
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

    public static final String KEY_PREF_SERVICE = "pref_service";

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key)
    {
        if (key.equals(KEY_PREF_SERVICE)) {
            CheckBoxPreference servicePref = (CheckBoxPreference)findPreference(key);

            if(servicePref.isChecked()){
                launchService();
            }
            else{
                stopService();
            }
            // Set summary to be the user-description for the selected value
            //servicePref.setSummary(sharedPreferences.getString(key, ""));
        }
    }


    private void launchService(){
        Intent myIntent = new Intent(getActivity(), WeatherStationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getActivity()
                .getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10); // first time
        long frequency= 1 * 60 * 1000; // in ms
        //AlarmManager.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);

    }

    private void stopService() {
        Intent myIntent = new Intent(getActivity(), WeatherStationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)getActivity()
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
