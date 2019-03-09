package com.rdev.tryp.model.ride_responce;

import com.google.gson.annotations.SerializedName;

public class RideResponse {

    @SerializedName("data")
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return
                "RideResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}