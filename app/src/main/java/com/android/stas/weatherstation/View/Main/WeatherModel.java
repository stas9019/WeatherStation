package com.android.stas.weatherstation.View.Main;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.android.stas.weatherstation.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by root on 10.07.16.
 */
public class WeatherModel {


    public static final String KEY_PREF_STATION_IP = "pref_stationIP";

    private static final String TEMPERATURE = "Temperature:";
    private static final String ERROR = "Error:";

    private WeatherAdapter activity;

    private RequestQueue queue;

    public WeatherModel(WeatherAdapter activity){
        this.activity = activity;
        PreferenceManager.setDefaultValues((Context)activity, R.xml.preferences, false);
        queue = Volley.newRequestQueue((Context)activity);
    }


    public void update(){

        StringRequest request = createRequest();
        queue.add(request);

    }


    private StringRequest createRequest(){

        String url = getUrlFromPreferences();

        final StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);


                        if (response.startsWith(TEMPERATURE)){
                            String[] data = response.split(",");
                            String temperature = data[0];
                            String humidity = data[1];

                            activity.presentResult(temperature, humidity);
                        }
                        else{
                            activity.presentResult(ERROR, ERROR);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        activity.presentResult(ERROR, ERROR);
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }

    private String getUrlFromPreferences(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences((Context)activity);
        String stationIP = sharedPref.getString(KEY_PREF_STATION_IP, "");

        System.out.println("stationIP " + stationIP);

        return "http://"+stationIP;
    }

}
