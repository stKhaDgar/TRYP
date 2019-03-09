package com.rdev.tryp.model.favourite_driver;

import com.google.gson.annotations.SerializedName;

public class FavouriteDriver {

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
                "FavouriteDriver{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}