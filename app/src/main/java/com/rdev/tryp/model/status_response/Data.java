package com.rdev.tryp.model.status_response;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ride")
    private Ride ride;

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Ride getRide() {
        return ride;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "ride = '" + ride + '\'' +
                        "}";
    }
}