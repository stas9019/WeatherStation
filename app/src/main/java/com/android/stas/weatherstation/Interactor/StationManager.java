package com.android.stas.weatherstation.Interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.stas.weatherstation.R;
import com.android.stas.weatherstation.model.Requester;
import com.android.stas.weatherstation.model.WeatherEntry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Stas Zamana  on 15.08.16.
 */
public class StationManager {

    public static final String KEY_PREF_STATION_IP = "pref_stationIP";
    private static final String TEMPERATURE = "Temperature:";

    private Requester requester;
    private Context context;

    private RequestQueue queue;

    public StationManager(Requester requester, Context context){
        this.requester = requester;
        this.context = context;

        queue = Volley.newRequestQueue(context);
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
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

                            String date = getFormattedDate();
                            requester.presentResult(date, temperature, humidity);
                            saveData(temperature, humidity);
                        }
                        else{
                            requester.showError();
                            //activity.presentResult(ERROR, ERROR);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        requester.showError();
                        //activity.presentResult(ERROR, ERROR);
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }

    private void saveData(String temperature, String humidity){

        String date = getFormattedDate();
        WeatherEntry entry =  new WeatherEntry(date, temperature, humidity);
        entry.save();
        //contract.insert(date, temperature, humidity);
    }


    private String getFormattedDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();

        return dateFormat.format(date);
    }

    private String getUrlFromPreferences(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String stationIP = sharedPref.getString(KEY_PREF_STATION_IP, "");

        System.out.println("stationIP " + stationIP);

        return "http://"+stationIP;
    }

}
