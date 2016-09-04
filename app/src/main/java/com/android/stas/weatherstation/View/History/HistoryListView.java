package com.android.stas.weatherstation.View.History;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

/**
 * Created by root on 14.08.16.
 */
public class HistoryListView extends ListActivity{

    ProgressBar progressBar;
    HistoryListViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();

        model = new HistoryListViewModel(this);
        ListAdapter adapter = model.getAdapter();
        setListAdapter(adapter);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);


    }
    private void hideStatusBar(){

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}
