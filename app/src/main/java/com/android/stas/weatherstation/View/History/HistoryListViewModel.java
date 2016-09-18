package com.android.stas.weatherstation.View.History;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.stas.weatherstation.Model.Constants;
import com.android.stas.weatherstation.Model.WeatherEvent;
import com.android.stas.weatherstation.R;
import com.android.stas.weatherstation.Model.WeatherEntry;

import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.List;

/**
 * @author Stas Zamana on 14.08.16.
 */
public class HistoryListViewModel{

    private Context context;

    private List<WeatherEntry> entries;
    private WeatherAdapter adapter;


    public HistoryListViewModel(Context context){
        this.context = context;
    }

    public ArrayAdapter getAdapter(){

        entries = WeatherEntry.listAll(WeatherEntry.class);

        Collections.reverse(entries);
        adapter =  new WeatherAdapter(context, entries);
        return adapter;
    }

    public class WeatherAdapter extends ArrayAdapter<WeatherEntry> {

        public WeatherAdapter(Context context, List<WeatherEntry> entries) {
            super(context, 0, entries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            WeatherEntry entry = entries.get(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }
            // Lookup view for data population
            TextView date = (TextView) convertView.findViewById(R.id.date_text);
            TextView time = (TextView) convertView.findViewById(R.id.time_text);
            TextView temp = (TextView) convertView.findViewById(R.id.temp_text);
            TextView hum  = (TextView) convertView.findViewById(R.id.hum_text);
            // Populate the data into the template view using the data object

            String[] data = entry.date.split(" ");

            date.setText(data[0]);
            time.setText(data[1]);
            temp.setText(entry.temperature+ Constants.TEMPERATURE_SYMBOL);
            hum.setText(entry.humidity+Constants.HUMIDITY_SYMBOL);
            // Return the completed view to render on screen
            return convertView;
        }
    }


}