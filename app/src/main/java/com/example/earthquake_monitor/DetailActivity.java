package com.example.earthquake_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        EarthQuake earthquake = extras.getParcelable(MainActivity.SELECTED_EARTHQUAKE);

        if (earthquake != null) {
            TextView magnitudeTextView = findViewById(R.id.eq_detail_magnitude);
            TextView longitudeTextView = findViewById(R.id.eq_detail_longitude);
            TextView latitudeTextView = findViewById(R.id.eq_detail_latitude);
            TextView placeTextView = findViewById(R.id.eq_detail_place);
            TextView dateTimeTextView = findViewById(R.id.eq_detail_date_time);

            magnitudeTextView.setText(String.valueOf(earthquake.getMagnitude()));
            longitudeTextView.setText(earthquake.getLongitude());
            latitudeTextView.setText(earthquake.getLatitude());
            placeTextView.setText(earthquake.getPlace());
            dateTimeTextView.setText(getStringDateFromTimestamp(earthquake.getDateTime()));
        }

    }

    private String getStringDateFromTimestamp(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy - H:mm:ss", Locale.getDefault());

        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

}
