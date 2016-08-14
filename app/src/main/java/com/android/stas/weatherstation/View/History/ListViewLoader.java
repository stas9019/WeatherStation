package com.android.stas.weatherstation.View.History;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import com.android.stas.weatherstation.Model.WeatherRecordContract;
import com.android.stas.weatherstation.R;

/**
 * Created by root on 14.08.16.
 */
public class ListViewLoader extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    SimpleCursorAdapter mAdapter;
    WeatherRecordContract contract;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,
                AbsListView.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);

        String[] fromColumns = WeatherRecordContract.PROJECTION;
        int[] toViews = {R.id.date_text, R.id.temp_text, R.id.hum_text};


        contract = new WeatherRecordContract(this);
        //contract.clearTest();
        //contract.insert("test","test","test");
        //Cursor cursor = contract.fetch();

        mAdapter = new SimpleCursorAdapter(this, R.layout.list_item, null ,
                fromColumns, toViews, 0);

        setListAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, null,
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
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        progressBar.setVisibility(View.VISIBLE);
    }
}
