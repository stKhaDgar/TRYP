package com.rdev.tryp.model.favourite_driver;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("drivers")
    private List<com.rdev.tryp.model.DriversItem> drivers;

    public void setDrivers(List<com.rdev.tryp.model.DriversItem> drivers) {
        this.drivers = drivers;
    }

    public List<com.rdev.tryp.model.DriversItem> getDrivers() {
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