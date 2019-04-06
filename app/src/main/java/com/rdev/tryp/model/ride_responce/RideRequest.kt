package com.rdev.tryp.model.ride_responce

import com.google.gson.annotations.SerializedName


class RideRequest {

    @SerializedName("is_asap")
    var isAsap: Int = 0

    @SerializedName("pickup_lng")
    var pickupLng: String? = null

    @SerializedName("destination_lng")
    var destinationLng: String? = null

    @SerializedName("driver_id")
    var driverId: String? = null

    @SerializedName("destination_address")
    var destinationAddress: String? = null

    @SerializedName("user_id")
    var userId: String? = null

    @SerializedName("destination_lat")
    var destinationLat: String? = null

    @SerializedName("pickup_address")
    var pickupAddress: String? = null

    @SerializedName("pickup_time")
    var pickupTime: String? = null

    @SerializedName("ip_address")
    var ipAddress: String? = null

    @SerializedName("pickup_lat")
    var pickupLat: String? = null

    @SerializedName("ride_request_id")
    var requestId: String? = null

    override fun toString(): String {
        return "RideRequest{" +
                "is_asap = '" + isAsap + '\''.toString() +
                ",pickup_lng = '" + pickupLng + '\''.toString() +
                ",destination_lng = '" + destinationLng + '\''.toString() +
                ",driver_id = '" + driverId + '\''.toString() +
                ",destination_address = '" + destinationAddress + '\''.toString() +
                ",user_id = '" + userId + '\''.toString() +
                ",destination_lat = '" + destinationLat + '\''.toString() +
                ",pickup_address = '" + pickupAddress + '\''.toString() +
                ",pickup_time = '" + pickupTime + '\''.toString() +
                ",ip_address = '" + ipAddress + '\''.toString() +
                ",pickup_lat = '" + pickupLat + '\''.toString() +
                ",request_id = '" + requestId + '\''.toString() +
                "}"
    }

}