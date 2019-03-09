package com.rdev.tryp.model.status_response;

public class RideRequest {
    private int isAsap;
    private String pickupLng;
    private String destinationLng;
    private String driverId;
    private String rideRequestId;
    private String voucher_no;

    public String getVoucher_no() {
        return voucher_no;
    }

    public void setVoucher_no(String voucher_no) {
        this.voucher_no = voucher_no;
    }

    private String destinationAddress;
    private String userId;
    private String destinationLat;
    private String pickupAddress;
    private String pickupTime;
    private String ipAddress;
    private String pickupLat;

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

    public void setRideRequestId(String rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public String getRideRequestId() {
        return rideRequestId;
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

    @Override
    public String toString() {
        return
                "RideRequest{" +
                        "is_asap = '" + isAsap + '\'' +
                        ",pickup_lng = '" + pickupLng + '\'' +
                        ",destination_lng = '" + destinationLng + '\'' +
                        ",driver_id = '" + driverId + '\'' +
                        ",ride_request_id = '" + rideRequestId + '\'' +
                        ",destination_address = '" + destinationAddress + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",destination_lat = '" + destinationLat + '\'' +
                        ",pickup_address = '" + pickupAddress + '\'' +
                        ",pickup_time = '" + pickupTime + '\'' +
                        ",ip_address = '" + ipAddress + '\'' +
                        ",pickup_lat = '" + pickupLat + '\'' +
                        "}";
    }
}
