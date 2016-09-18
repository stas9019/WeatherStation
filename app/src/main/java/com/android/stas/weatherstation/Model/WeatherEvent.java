package com.android.stas.weatherstation.Model;

/**
 * @author  Stas Zamana on 04.09.16.
 */
public class WeatherEvent {

    public final String date;
    public final String temperature;
    public final String humidity;

    public WeatherEvent(String date, String temperature, String humidity) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
