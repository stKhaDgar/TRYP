package com.rdev.tryp.model.status_response

import com.google.gson.annotations.SerializedName


class Ride {

    @SerializedName("is_asap")
    var isAsap: Int = 0

    @SerializedName("pickup_lng")
    var pickupLng: Double = 0.toDouble()

    @SerializedName("destination_lng")
    var destinationLng: Double = 0.toDouble()

    @SerializedName("is_accepted")
    var isAccepted: Int = 0

    @SerializedName("driver_id")
    var driverId: Int = 0

    @SerializedName("destination_address")
    var destinationAddress: String? = null

    @SerializedName("voucher_no")
    var voucherNo: String? = null

    @SerializedName("pickup_address")
    var pickupAddress: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("ip_address")
    var ipAddress: String? = null

    @SerializedName("ride_request_id")
    var rideRequestId: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("user_id")
    var userId: Int = 0

    @SerializedName("destination_lat")
    var destinationLat: Double = 0.toDouble()

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("pickup_time")
    var pickupTime: String? = null

    @SerializedName("pickup_lat")
    var pickupLat: Double = 0.toDouble()

    @SerializedName("ride_status")
    var rideStatus: String? = null

    override fun toString(): String {
        return "Ride{" +
                "is_asap = '" + isAsap + '\''.toString() +
                ",pickup_lng = '" + pickupLng + '\''.toString() +
                ",destination_lng = '" + destinationLng + '\''.toString() +
                ",is_accepted = '" + isAccepted + '\''.toString() +
                ",driver_id = '" + driverId + '\''.toString() +
                ",destination_address = '" + destinationAddress + '\''.toString() +
                ",voucher_no = '" + voucherNo + '\''.toString() +
                ",pickup_address = '" + pickupAddress + '\''.toString() +
                ",created_at = '" + createdAt + '\''.toString() +
                ",ip_address = '" + ipAddress + '\''.toString() +
                ",ride_request_id = '" + rideRequestId + '\''.toString() +
                ",updated_at = '" + updatedAt + '\''.toString() +
                ",user_id = '" + userId + '\''.toString() +
                ",destination_lat = '" + destinationLat + '\''.toString() +
                ",id = '" + id + '\''.toString() +
                ",pickup_time = '" + pickupTime + '\''.toString() +
                ",pickup_lat = '" + pickupLat + '\''.toString() +
                ",ride_status = '" + rideStatus + '\''.toString() +
                "}"
    }

}