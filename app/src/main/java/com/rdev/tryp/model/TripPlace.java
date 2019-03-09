package com.rdev.tryp.model;

import com.google.android.gms.maps.model.LatLng;

public class TripPlace {
    private String locale;
    private LatLng coord;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public LatLng getCoord() {
        return coord;
    }

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    public TripPlace() {
    }

    public TripPlace(String locale, LatLng coord) {
        this.locale = locale;
        this.coord = coord;
    }
}
