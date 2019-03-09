package com.rdev.tryp.model.ride_responce;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    private String message;

    @SerializedName("ride_request")
    private RideRequest rideRequest;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setRideRequest(RideRequest rideRequest) {
        this.rideRequest = rideRequest;
    }

    public RideRequest getRideRequest() {
        return rideRequest;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "message = '" + message + '\'' +
                        ",ride_request = '" + rideRequest + '\'' +
                        "}";
    }
}