package com.example.earthquake_monitor;

public class EarthQuake {

    private String magnitude;
    private String place;

    public EarthQuake(String magnitude, String place) {

        this.magnitude = magnitude;
        this.place = place;

    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }
} // end EarthQuake class ()


