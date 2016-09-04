package com.android.stas.weatherstation.Interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.stas.weatherstation.model.Requester;
import com.android.stas.weatherstation.model.WeatherEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Stas Zamana  on 15.08.16.
 */
public class WeatherStationService extends BroadcastReceiver implements Requester {

    private static final String TAG = "WeatherStationService";

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
        Log.d(TAG, "posting event "+date+" "+temperature+" "+humidity);
        EventBus.getDefault().post(new WeatherEvent(date,temperature, humidity));
    }

    @Override
    public void showError() {

    }
}