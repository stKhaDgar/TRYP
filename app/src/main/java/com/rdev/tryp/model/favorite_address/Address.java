package com.rdev.tryp.model.favorite_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}