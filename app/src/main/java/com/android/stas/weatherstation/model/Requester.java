package com.android.stas.weatherstation.model;

/**
 * Created by root on 15.08.16.
 */
public interface Requester {

    void presentResult(String date, String temperature, String humidity);
    void showError();

}
