package com.rdev.tryp.model.ride_responce;

import com.google.gson.annotations.SerializedName;

public class RideRequest {

    @SerializedName("is_asap")
    private int isAsap;

    @SerializedName("pickup_lng")
    private String pickupLng;

    @SerializedName("destination_lng")
    private String destinationLng;

    @SerializedName("driver_id")
    private String driverId;

    @SerializedName("destination_address")
    private String destinationAddress;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("destination_lat")
    private String destinationLat;

    @SerializedName("pickup_address")
    private String pickupAddress;

    @SerializedName("pickup_time")
    private String pickupTime;

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("pickup_lat")
    private String pickupLat;

    @SerializedName("ride_request_id")
    private String requestId;

    public void setIsAsap(int isAsap) {
        this.isAsap = isAsap;
    }

    public int getIsAsap() {
        return isAsap;
    }

    public void setPickupLng(String pickupLng) {
        this.pickupLng = pickupLng;
    }

    public String getPickupLng() {
        return pickupLng;
    }

    public void setDestinationLng(String destinationLng) {
        this.destinationLng = destinationLng;
    }

    public String getDestinationLng() {
        return destinationLng;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setPickupLat(String pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getPickupLat() {
        return pickupLat;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return
                "RideRequest{" +
                        "is_asap = '" + isAsap + '\'' +
                        ",pickup_lng = '" + pickupLng + '\'' +
                        ",destination_lng = '" + destinationLng + '\'' +
                        ",driver_id = '" + driverId + '\'' +
                        ",destination_address = '" + destinationAddress + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",destination_lat = '" + destinationLat + '\'' +
                        ",pickup_address = '" + pickupAddress + '\'' +
                        ",pickup_time = '" + pickupTime + '\'' +
                        ",ip_address = '" + ipAddress + '\'' +
                        ",pickup_lat = '" + pickupLat + '\'' +
                        ",request_id = '" + requestId + '\'' +
                        "}";
    }
}