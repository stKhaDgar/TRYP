package com.rdev.tryp.model.status_response;

import com.google.gson.annotations.SerializedName;

public class Ride {

    @SerializedName("is_asap")
    private int isAsap;

    @SerializedName("pickup_lng")
    private double pickupLng;

    @SerializedName("destination_lng")
    private double destinationLng;

    @SerializedName("is_accepted")
    private int isAccepted;

    @SerializedName("driver_id")
    private int driverId;

    @SerializedName("destination_address")
    private String destinationAddress;

    @SerializedName("voucher_no")
    private String voucherNo;

    @SerializedName("pickup_address")
    private String pickupAddress;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("ride_request_id")
    private String rideRequestId;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("destination_lat")
    private double destinationLat;

    @SerializedName("id")
    private int id;

    @SerializedName("pickup_time")
    private String pickupTime;

    @SerializedName("pickup_lat")
    private double pickupLat;

    @SerializedName("ride_status")
    private String rideStatus;

    public void setIsAsap(int isAsap) {
        this.isAsap = isAsap;
    }

    public int getIsAsap() {
        return isAsap;
    }

    public void setPickupLng(double pickupLng) {
        this.pickupLng = pickupLng;
    }

    public double getPickupLng() {
        return pickupLng;
    }

    public void setDestinationLng(double destinationLng) {
        this.destinationLng = destinationLng;
    }

    public double getDestinationLng() {
        return destinationLng;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setRideRequestId(String rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public String getRideRequestId() {
        return rideRequestId;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupLat(double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public double getPickupLat() {
        return pickupLat;
    }

    public void setRideStatus(String rideStatus) {
        this.rideStatus = rideStatus;
    }

    public String getRideStatus() {
        return rideStatus;
    }

    @Override
    public String toString() {
        return
                "Ride{" +
                        "is_asap = '" + isAsap + '\'' +
                        ",pickup_lng = '" + pickupLng + '\'' +
                        ",destination_lng = '" + destinationLng + '\'' +
                        ",is_accepted = '" + isAccepted + '\'' +
                        ",driver_id = '" + driverId + '\'' +
                        ",destination_address = '" + destinationAddress + '\'' +
                        ",voucher_no = '" + voucherNo + '\'' +
                        ",pickup_address = '" + pickupAddress + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",ip_address = '" + ipAddress + '\'' +
                        ",ride_request_id = '" + rideRequestId + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",destination_lat = '" + destinationLat + '\'' +
                        ",id = '" + id + '\'' +
                        ",pickup_time = '" + pickupTime + '\'' +
                        ",pickup_lat = '" + pickupLat + '\'' +
                        ",ride_status = '" + rideStatus + '\'' +
                        "}";
    }
}