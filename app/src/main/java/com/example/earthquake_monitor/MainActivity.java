package com.example.earthquake_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView earthQuakeListView = findViewById(R.id.earthquakeListView);
        ArrayList<EarthQuake> eqList = new ArrayList<>();

        eqList.add(new EarthQuake("4.6", "97 km S of Wonosari, Indonesia"));
        eqList.add(new EarthQuake("2.3", "16 km S of Joshua Tree, CA"));
        eqList.add(new EarthQuake("3.1", "97 km S of Wonosari, Indonesia"));
        eqList.add(new EarthQuake("4.6", "97 km S of Wonosari, Indonesia"));
        eqList.add(new EarthQuake("2.3", "16 km S of Joshua Tree, CA"));
        eqList.add(new EarthQuake("3.1", "97 km S of Wonosari, Indonesia"));
        eqList.add(new EarthQuake("4.6", "97 km S of Wonosari, Indonesia"));
        eqList.add(new EarthQuake("2.3", "16 km S of Joshua Tree, CA"));
        eqList.add(new EarthQuake("3.1", "97 km S of Wonosari, Indonesia"));


        // ListView always must continue with an adapter
        EarthQuakeAdapter eqAdapter = new EarthQuakeAdapter(this, R.layout.eq_list_item, eqList);
        earthQuakeListView.setAdapter(eqAdapter);
    }
}
