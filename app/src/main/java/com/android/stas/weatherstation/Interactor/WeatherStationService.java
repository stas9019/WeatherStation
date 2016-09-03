package com.android.stas.weatherstation.Interactor;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.stas.weatherstation.model.Requester;

/**
 * Created by root on 15.08.16.
 */
public class WeatherStationService extends BroadcastReceiver implements Requester {

    private StationManager manager;


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println( "service starting");
        Toast.makeText(context, "service starting", Toast.LENGTH_LONG).show();
        manager = new StationManager(this, context);
        manager.update();
    }

    @Override
    public void presentResult(String date, String temperature, String humidity) {

    }

    @Override
    public void showError() {

    }
}