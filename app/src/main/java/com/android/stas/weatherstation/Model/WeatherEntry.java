package com.android.stas.weatherstation.Model;

import com.orm.SugarRecord;

/**
 * Created by root on 15.08.16.
 */
public class WeatherEntry extends SugarRecord {

    public String date = "";
    public String temperature = "";
    public String humidity = "";


    public WeatherEntry(){}

    public WeatherEntry(String date, String temperature, String humidity){
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
    }

}
