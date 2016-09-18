package com.android.stas.weatherstation.View.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.stas.weatherstation.R;
import com.android.stas.weatherstation.View.History.HistoryListView;
import com.android.stas.weatherstation.View.Settings.SettingsActivity;

import org.greenrobot.eventbus.EventBus;


public class WeatherActivity extends AppCompatActivity implements WeatherAdapter {

    private static final String TAG = "WeatherActivity";

    private static final String TEMPERATURE = "Temperature";
    private static final String HUMIDITY = "Humidity";
    private static final String LAST_UPDATE = "Last update";

    private static final String TEMPERATURE_SYMBOL = " °C";
    private static final String HUMIDITY_SYMBOL = " %";


    private ImageButton mUpdateButton;
    private ImageButton mHistoryButton;
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

        mUpdateButton = (ImageButton) findViewById(R.id.updateButton);
        mHistoryButton = (ImageButton) findViewById(R.id.historyButton);

        if(savedInstanceState != null){

            mLastUpdateTextView.setText(savedInstanceState.getString(LAST_UPDATE, "–"));
            mTemperatureTextView.setText(savedInstanceState.getString(TEMPERATURE, "–"));
            mHumidityTextView.setText(savedInstanceState.getString(HUMIDITY, "–"));
        }

        hideStatusBar();


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
    }

    private void hideStatusBar(){

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Model will register for events");
        EventBus.getDefault().register(model);
    }
    @Override
    public void onStop() {
        Log.d(TAG, "Model will unregister from events");
        EventBus.getDefault().unregister(model);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.getLastWeather();
    }

    private void openHistory(){
        Intent intent = new Intent(this, HistoryListView.class);
        startActivity(intent);
    }



    public void presentResult(String date, String temperature, String humidity){

//        if (!temperature.startsWith(TEMPERATURE)){
//            temperature = TEMPERATURE + temperature;
//            humidity = HUMIDITY + humidity;
//        }


        mTemperatureTextView.setText(temperature + TEMPERATURE_SYMBOL);
        mHumidityTextView.setText(humidity + HUMIDITY_SYMBOL);
        mLastUpdateTextView.setText(date);
    }



    public void showError(){
        Toast.makeText(this, R.string.errorToast, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        Log.d(TAG, "onSaveInstanceState called");
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

}
