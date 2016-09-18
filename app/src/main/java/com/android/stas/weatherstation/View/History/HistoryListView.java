package com.android.stas.weatherstation.View.History;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.android.stas.weatherstation.Model.WeatherEntry;
import com.android.stas.weatherstation.Model.WeatherEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Stas Zamana on 14.08.16.
 */
public class HistoryListView extends ListActivity{

    HistoryListViewModel model;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();

        model = new HistoryListViewModel(this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("On resume caller");
        adapter = model.getAdapter();
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void hideStatusBar(){

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onStart() {
        super.onStart();
        System.out.println("On start caller");
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onWeatherEvent(WeatherEvent event){
        //adapter.add();
        adapter.insert(new WeatherEntry(event.date, event.temperature, event.humidity), 0);
        System.out.println("On stopped caller");
    }

}
