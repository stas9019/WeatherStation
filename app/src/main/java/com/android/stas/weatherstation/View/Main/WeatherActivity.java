package com.android.stas.weatherstation.View.Main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.stas.weatherstation.R;
import com.android.stas.weatherstation.View.History.ListViewLoader;
import com.android.stas.weatherstation.View.Settings.SettingsActivity;


public class WeatherActivity extends AppCompatActivity implements WeatherAdapter {

    private static final String TAG = "WeatherActivity";
    private static final String KEY_INDEX = "index";

    private static final String TEMPERATURE = "Temperature:";
    private static final String HUMIDITY = "Humidity:";

    private Button mUpdateButton;
    private Button mHistoryButton;
    private TextView mTemperatureTextView;
    private TextView mHumidityTextView;

    private WeatherModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        mTemperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        mHumidityTextView = (TextView) findViewById(R.id.humidityTextView);
        mUpdateButton = (Button) findViewById(R.id.updateButton);
        mHistoryButton = (Button) findViewById(R.id.historyButton);

        if(savedInstanceState != null){
            mTemperatureTextView.setText(savedInstanceState.getString(TEMPERATURE, TEMPERATURE + " 0"));
            mHumidityTextView.setText(savedInstanceState.getString(HUMIDITY, HUMIDITY + " 0"));
        }



        model = new WeatherModel(this);

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
    }

    private void openHistory(){
        Intent intent = new Intent(this, ListViewLoader.class);
        startActivity(intent);
    }



    public void presentResult(String temperature, String humidity){

        mTemperatureTextView.setText(TEMPERATURE + temperature);
        mHumidityTextView.setText(HUMIDITY + humidity);

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        Log.d(TAG, "onSaveInstanceState called");
        //savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
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
