package com.android.stas.weatherstation.Model;

/**
 * Created by root on 15.08.16.
 */
public interface Requester {

    void presentResult(String date, String temperature, String humidity);
    void showError();

}
