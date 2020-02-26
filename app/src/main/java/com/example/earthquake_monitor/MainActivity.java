package com.example.earthquake_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadEqsAsyncTask.DownloadEqsInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView earthQuakeListView = findViewById(R.id.earthquakeListView);
        ArrayList<EarthQuake> eqList = new ArrayList<>();


        eqList.add(new EarthQuake("4.6", "97 km S of Wonosari, Indonesia"));
        eqList.add(new EarthQuake("2.3", "16 km S of Joshua Tree, CA"));
        eqList.add(new EarthQuake("3.1", "97 km S of Wonosari, Indonesia"));

        // ListView always must continue with an adapter
        EarthQuakeAdapter eqAdapter = new EarthQuakeAdapter(this, R.layout.eq_list_item, eqList);
        earthQuakeListView.setAdapter(eqAdapter);

        DownloadEqsAsyncTask downloadEqsAsyncTask = new DownloadEqsAsyncTask();
        downloadEqsAsyncTask.delegate = this;

        try {
            downloadEqsAsyncTask.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

    } // end onCreate()

    @Override
    public void onEqsDownloaded(String eqsData) {
        Log.d("MANZANA", eqsData);
    }
} // End MainActivity()
