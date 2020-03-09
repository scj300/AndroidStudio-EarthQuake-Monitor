package com.example.earthquake_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadEqsAsyncTask.DownloadEqsInterface {

    public static final String SELECTED_EARTHQUAKE = "selected_earthquake";
    protected ListView earthQuakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earthQuakeListView = findViewById(R.id.earthquakeListView);

        // Network available
        if(MyUtils.isNetworkAvailable(this)) {
            downloadEarthquakes();
        } // end if (Utils.isNetworkAvailable(this))

        // No network connection
        // Read from Database
        else {
            getEarthquakesFromDB();
        }

    } // end onCreate()

    private void getEarthquakesFromDB() {

        EarthquakeDbHelper dbHelper = new EarthquakeDbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(EarthquakeContract.EarthquakeColumns.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<EarthQuake> eqList = new ArrayList<>();

        while(cursor.moveToNext()) {
            Long dateTime = cursor.getLong(EarthquakeContract.EarthquakeColumns.TIMESTAMP_COLUMN_INDEX);
            double magnitude = cursor.getDouble(EarthquakeContract.EarthquakeColumns.MAGNITUDE_COLUMN_INDEX);
            String place = cursor.getString(EarthquakeContract.EarthquakeColumns.PLACE_COLUMN_INDEX);
            String longitude = cursor.getString(EarthquakeContract.EarthquakeColumns.LONGITUDE_COLUMN_INDEX);
            String latitude = cursor.getString(EarthquakeContract.EarthquakeColumns.LATITUDE_COLUMN_INDEX);

            eqList.add(new EarthQuake(dateTime, magnitude, place, longitude, latitude));
        }

        cursor.close();

        fillEarthquakesList(eqList);
    }

    private void downloadEarthquakes() {
        DownloadEqsAsyncTask downloadEqsAsyncTask = new DownloadEqsAsyncTask(this);
        downloadEqsAsyncTask.delegate = this;

        try {
            downloadEqsAsyncTask.execute(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onEqsDownloaded(ArrayList<EarthQuake> eqList) {
        fillEarthquakesList(eqList);
    }

    private void fillEarthquakesList(ArrayList<EarthQuake> eqList) {
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
