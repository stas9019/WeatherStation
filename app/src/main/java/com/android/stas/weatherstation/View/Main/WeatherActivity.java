package com.android.stas.weatherstation.View.Main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.stas.weatherstation.Interactor.WeatherStationService;
import com.android.stas.weatherstation.R;
import com.android.stas.weatherstation.View.History.HistoryListView;
import com.android.stas.weatherstation.View.Settings.SettingsActivity;

import java.util.Calendar;


public class WeatherActivity extends AppCompatActivity implements WeatherAdapter {

    private static final String TAG = "WeatherActivity";

    private static final String TEMPERATURE = "Temperature:";
    private static final String HUMIDITY = "Humidity:";
    private static final String LAST_UPDATE = "Last update: ";

    private Button mUpdateButton;
    private Button mHistoryButton;
    private TextView mTemperatureTextView;
    private TextView mLastUpdateTextView;
    private TextView mHumidityTextView;

    private WeatherModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        mTemperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        mHumidityTextView = (TextView) findViewById(R.id.humidityTextView);
        mLastUpdateTextView = (TextView) findViewById(R.id.lastUpdateTextView);

        mUpdateButton = (Button) findViewById(R.id.updateButton);
        mHistoryButton = (Button) findViewById(R.id.historyButton);

        if(savedInstanceState != null){

            mLastUpdateTextView.setText(savedInstanceState.getString(LAST_UPDATE, LAST_UPDATE + " –"));
            mTemperatureTextView.setText(savedInstanceState.getString(TEMPERATURE, TEMPERATURE + " –"));
            mHumidityTextView.setText(savedInstanceState.getString(HUMIDITY, HUMIDITY + " –"));
        }



        model = new WeatherModel(this);
        model.getLastWeather();

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.update();
            }
        });

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });

        //scheduleService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.getLastWeather();
    }

    private void scheduleService(){
        Intent myIntent = new Intent(this, WeatherStationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5); // first time
        long frequency= 5 * 1000; // in ms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);
    }

    private void openHistory(){
        Intent intent = new Intent(this, HistoryListView.class);
        startActivity(intent);
    }



    public void presentResult(String date, String temperature, String humidity){

        if (!temperature.startsWith(TEMPERATURE)){
            temperature = TEMPERATURE + temperature;
            humidity = HUMIDITY + humidity;
        }

        mLastUpdateTextView.setText(LAST_UPDATE + date);
        mTemperatureTextView.setText(temperature);
        mHumidityTextView.setText(humidity);
    }



    public void showError(){
        Toast.makeText(this, R.string.errorToast, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        Log.d(TAG, "onSaveInstanceState called");
        //savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putString(LAST_UPDATE, mLastUpdateTextView.toString());
        savedInstanceState.putString(TEMPERATURE, mTemperatureTextView.toString());
        savedInstanceState.putString(HUMIDITY, mHumidityTextView.toString());
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    */
}
