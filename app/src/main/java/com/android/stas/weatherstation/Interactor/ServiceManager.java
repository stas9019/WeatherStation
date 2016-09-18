package com.android.stas.weatherstation.Interactor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.stas.weatherstation.Model.Constants;

import java.util.Calendar;

/**
 * Created by root on 04.09.16.
 */
public class ServiceManager extends BroadcastReceiver {

    private static final String KEY_PREF_FREQUENCY = "pref_frequency";

    Context activity;

    public ServiceManager(){

    }

    public ServiceManager(Context activity){
        this.activity = activity;
    }

    private Context getActivity(){
        return activity;
    }

    public void launchService(){
        Intent myIntent = new Intent(getActivity(), WeatherUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getActivity()
                .getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10); // first time

        int minutes = getFrequencyFromPreferences();
        long frequency= minutes * 60 * 1000; // in ms

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);
        enableServiceAfterRelaunch(true);
    }

    public void stopService() {
        Intent myIntent = new Intent(getActivity(), WeatherUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)getActivity()
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        enableServiceAfterRelaunch(false);
    }

    private void enableServiceAfterRelaunch(boolean shouldEnable){
        ComponentName receiver = new ComponentName(getActivity(), ServiceManager.class);
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                shouldEnable? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }



    private int getFrequencyFromPreferences(){
        String frequencyText = getSharedPreference().getString(KEY_PREF_FREQUENCY, "5");
        int frequency = Integer.parseInt(frequencyText);

        System.out.println("frequency " + frequency);

        if(frequency < 1){
            frequency = 5;
        }

        return frequency;
    }


    private SharedPreferences getSharedPreference(){
        return PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            activity = context;
            Toast.makeText(context, "Launching service after reload", Toast.LENGTH_LONG).show();
            launchService();
        }
    }

}
