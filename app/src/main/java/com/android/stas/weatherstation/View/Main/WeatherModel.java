package com.android.stas.weatherstation.View.Main;

import android.content.Context;
import android.preference.PreferenceManager;

import com.android.stas.weatherstation.Interactor.StationManager;
import com.android.stas.weatherstation.R;
import com.android.stas.weatherstation.model.Requester;
import com.android.stas.weatherstation.model.WeatherEntry;


/**
 * Created by root on 10.07.16.
 */
public class WeatherModel implements Requester{

    private WeatherAdapter activity;
    private Context context;
    private StationManager stationManager;

    public WeatherModel(WeatherAdapter activity){
        this.activity = activity;
        this.context = (Context)activity;
        stationManager = new StationManager(this, (Context)activity);

        PreferenceManager.setDefaultValues((Context) activity, R.xml.preferences, false);

    }

    public void update(){
        stationManager.update();
    }

    public void getLastWeather(){

        WeatherEntry entry = WeatherEntry.last(WeatherEntry.class);
        if (entry != null)
            presentResult(entry.date, entry.temperature, entry.humidity);

    }


    public void presentResult(String date, String temperature, String humidity){
        activity.presentResult(date, temperature, humidity);
    }

    public void showError(){
        activity.showError();
    }


}
