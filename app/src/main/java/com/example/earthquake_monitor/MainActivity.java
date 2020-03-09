package com.example.earthquake_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadEqsAsyncTask.DownloadEqsInterface {

    public static final String SELECTED_EARTHQUAKE = "selected_earthquake";
    protected ListView earthQuakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earthQuakeListView = findViewById(R.id.earthquakeListView);

        DownloadEqsAsyncTask downloadEqsAsyncTask = new DownloadEqsAsyncTask();
        downloadEqsAsyncTask.delegate = this;

        try {
            downloadEqsAsyncTask.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

    } // end onCreate()

    @Override
    public void onEqsDownloaded(ArrayList<EarthQuake> eqList) {

        // ListView always must continue with an adapter
        final EarthQuakeAdapter eqAdapter = new EarthQuakeAdapter(this, R.layout.eq_list_item, eqList);
        earthQuakeListView.setAdapter(eqAdapter);

        earthQuakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthQuake selectedEarthQuake = eqAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(SELECTED_EARTHQUAKE, selectedEarthQuake);

                startActivity(intent);
            }
        });
    }
} // End MainActivity()
