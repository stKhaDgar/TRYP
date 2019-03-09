package com.rdev.tryp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("drivers")
    private List<DriversItem> drivers;

    public void setDrivers(List<DriversItem> drivers) {
        this.drivers = drivers;
    }

    public List<DriversItem> getDrivers() {
        return drivers;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "drivers = '" + drivers + '\'' +
                        "}";
    }
}