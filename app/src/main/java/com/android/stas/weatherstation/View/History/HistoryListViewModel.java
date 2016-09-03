package com.android.stas.weatherstation.View.History;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.android.stas.weatherstation.R;
import com.android.stas.weatherstation.model.WeatherEntry;

import java.util.List;

/**
 * Created by root on 14.08.16.
 */
public class HistoryListViewModel{//} implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mAdapter;
    Context context;
    ListActivity activity;

    private List<WeatherEntry> entries;

    public HistoryListViewModel(Context context){
        this.context = context;
        activity = (ListActivity) context;
    }

    public ListAdapter getAdapter(){

        entries = WeatherEntry.listAll(WeatherEntry.class);
        return new WeatherAdapter(context, entries);
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
            TextView temp = (TextView) convertView.findViewById(R.id.temp_text);
            TextView hum  = (TextView) convertView.findViewById(R.id.hum_text);
            // Populate the data into the template view using the data object
            date.setText(entry.date);
            temp.setText(entry.temperature);
            hum.setText(entry.humidity);
            // Return the completed view to render on screen
            return convertView;
        }



    }

//    HistoryListViewModel(Context context){
//
//        this.context = context;
//        activity = (ListActivity) context;
//
//        String[] fromColumns = WeatherRecordContract.PROJECTION;
//        int[] toViews = {R.id.date_text, R.id.temp_text, R.id.hum_text};
//
//
//        contract = new WeatherRecordContract(activity);
//        contract.clearTest();
//        //contract.insert("today","22 C","53");
//        //Cursor cursor = contract.fetch();
//
//        mAdapter = new SimpleCursorAdapter(context, R.layout.list_item, null ,
//                fromColumns, toViews, 0);
//
//        activity.setListAdapter(mAdapter);
//
//        // Prepare the loader.  Either re-connect with an existing one,
//        // or start a new one.
//        activity.getLoaderManager().initLoader(0, null, this);
//    }
//
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//
//        return new CursorLoader(context, null,
//                WeatherRecordContract.PROJECTION, null, null, null)
//        {
//            @Override
//            public Cursor loadInBackground()
//            {
//                return contract.fetch();
//            }
//        };
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mAdapter.swapCursor(data);
//        //progressBar.setVisibility(View.INVISIBLE);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        mAdapter.swapCursor(null);
//        //progressBar.setVisibility(View.VISIBLE);
//    }


}
