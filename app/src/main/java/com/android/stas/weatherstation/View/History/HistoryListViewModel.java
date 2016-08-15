package com.android.stas.weatherstation.View.History;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.android.stas.weatherstation.Interactor.WeatherRecordContract;
import com.android.stas.weatherstation.R;

/**
 * Created by root on 14.08.16.
 */
public class HistoryListViewModel implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mAdapter;
    WeatherRecordContract contract;
    Context context;
    ListActivity activity;

    HistoryListViewModel(Context context){

        this.context = context;
        activity = (ListActivity) context;

        String[] fromColumns = WeatherRecordContract.PROJECTION;
        int[] toViews = {R.id.date_text, R.id.temp_text, R.id.hum_text};


        contract = new WeatherRecordContract(activity);
        contract.clearTest();
        //contract.insert("today","22 C","53");
        //Cursor cursor = contract.fetch();

        mAdapter = new SimpleCursorAdapter(context, R.layout.list_item, null ,
                fromColumns, toViews, 0);

        activity.setListAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        activity.getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(context, null,
                WeatherRecordContract.PROJECTION, null, null, null)
        {
            @Override
            public Cursor loadInBackground()
            {
                return contract.fetch();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        //progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        //progressBar.setVisibility(View.VISIBLE);
    }


}
