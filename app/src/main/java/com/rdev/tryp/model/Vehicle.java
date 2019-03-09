package com.rdev.tryp.model;

import com.google.gson.annotations.SerializedName;

public class Vehicle {

    @SerializedName("image")
    private String image;

    @SerializedName("color")
    private String color;

    @SerializedName("model")
    private String model;

    @SerializedName("plate_number")
    private String plateNumber;

    @SerializedName("make")
    private String make;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMake() {
        return make;
    }

    @Override
    public String toString() {
        return
                "Vehicle{" +
                        "image = '" + image + '\'' +
                        ",color = '" + color + '\'' +
                        ",model = '" + model + '\'' +
                        ",plate_number = '" + plateNumber + '\'' +
                        ",make = '" + make + '\'' +
                        "}";
    }
}