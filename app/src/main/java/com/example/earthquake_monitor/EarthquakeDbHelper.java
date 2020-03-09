package com.example.earthquake_monitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EarthquakeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "earthquakes.db";
    private static final int DATABASE_VERSION = 1;

    public EarthquakeDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@org.jetbrains.annotations.NotNull SQLiteDatabase sqLiteDatabase) {
        String EARTHQUAKES_DATABASE = "CREATE TABLE " + EarthquakeContract.EarthquakeColumns.TABLE_NAME + " (" +
                EarthquakeContract.EarthquakeColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EarthquakeContract.EarthquakeColumns.MAGNITUDE + " REAL NOT NULL," +
                EarthquakeContract.EarthquakeColumns.PLACE + " TEXT NOT NULL," +
                EarthquakeContract.EarthquakeColumns.LONGITUDE + " TEXT NOT NULL," +
                EarthquakeContract.EarthquakeColumns.LATITUDE + " TEXT NOT NULL," +
                EarthquakeContract.EarthquakeColumns.TIMESTAMP + " TEXT NOT NULL" +
                ")";

        sqLiteDatabase.execSQL(EARTHQUAKES_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + EarthquakeContract.EarthquakeColumns.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

} // end EarthquakeDbHelper
