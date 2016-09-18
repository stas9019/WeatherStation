package com.android.stas.weatherstation.Interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.stas.weatherstation.Model.Requester;
import com.android.stas.weatherstation.Model.WeatherEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Stas Zamana  on 15.08.16.
 */
public class WeatherUpdateService extends BroadcastReceiver implements Requester {

    private static final String TAG = "WeatherUpdateService";

    private StationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {

        manager = new StationManager(this, context);
        manager.update();
    }

    @Override
    public void presentResult(String date, String temperature, String humidity) {
        Log.d(TAG, "posting event " + date + " " + temperature + " " + humidity);
        EventBus.getDefault().post(new WeatherEvent(date, temperature, humidity));
    }

    @Override
    public void showError() {

    }
}