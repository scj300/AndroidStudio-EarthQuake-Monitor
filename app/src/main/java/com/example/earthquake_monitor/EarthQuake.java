package com.example.earthquake_monitor;

import android.os.Parcel;
import android.os.Parcelable;


public class EarthQuake implements Parcelable {

    private Long dateTime;
    private double magnitude;
    private String place;
    private String longitude;
    private String latitude;

    public EarthQuake(Long dateTime, double magnitude, String place, String longitude, String latitude) {
        this.dateTime = dateTime;
        this.magnitude = magnitude;
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public Long getDateTime() {
        return dateTime;
    }


    protected EarthQuake(Parcel in) {
        dateTime = in.readLong();
        magnitude = in.readDouble();
        place = in.readString();
        longitude = in.readString();
        latitude = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dateTime);
        dest.writeDouble(magnitude);
        dest.writeString(place);
        dest.writeString(longitude);
        dest.writeString(latitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<EarthQuake> CREATOR = new Parcelable.Creator<EarthQuake>() {
        @Override
        public EarthQuake createFromParcel(Parcel in) {
            return new EarthQuake(in);
        }

        @Override
        public EarthQuake[] newArray(int size) {
            return new EarthQuake[size];
        }
    };
}


