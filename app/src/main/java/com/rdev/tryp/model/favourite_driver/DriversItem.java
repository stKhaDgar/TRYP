package com.rdev.tryp.model.favourite_driver;

import com.google.gson.annotations.SerializedName;

public class DriversItem {

    @SerializedName("max_luggage")
    private int maxLuggage;

    @SerializedName("fare")
    private double fare;

    @SerializedName("max_passenger")
    private int maxPassenger;

    @SerializedName("driver")
    private Driver driver;

    @SerializedName("category")
    private String category;

    @SerializedName("type")
    private String type;

    @SerializedName("vehicle")
    private Vehicle vehicle;

    public void setMaxLuggage(int maxLuggage) {
        this.maxLuggage = maxLuggage;
    }

    public int getMaxLuggage() {
        return maxLuggage;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getFare() {
        return fare;
    }

    public void setMaxPassenger(int maxPassenger) {
        this.maxPassenger = maxPassenger;
    }

    public int getMaxPassenger() {
        return maxPassenger;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return
                "DriversItem{" +
                        "max_luggage = '" + maxLuggage + '\'' +
                        ",fare = '" + fare + '\'' +
                        ",max_passenger = '" + maxPassenger + '\'' +
                        ",driver = '" + driver + '\'' +
                        ",category = '" + category + '\'' +
                        ",type = '" + type + '\'' +
                        ",vehicle = '" + vehicle + '\'' +
                        "}";
    }
}