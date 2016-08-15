package com.android.stas.weatherstation.View.Main;

/**
 * Created by root on 10.07.16.
 */
public interface WeatherAdapter{

    void presentResult(String date, String temperature, String humidity);
    void showError();
}
