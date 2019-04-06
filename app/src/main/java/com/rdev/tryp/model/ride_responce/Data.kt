package com.rdev.tryp.model.ride_responce

import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("message")
    var message: String? = null
    @SerializedName("ride_request")
    var rideRequest: RideRequest? = null

    override fun toString(): String {
        return "Data{" +
                "message = '" + message + '\''.toString() +
                ",ride_request = '" + rideRequest + '\''.toString() +
                "}"
    }

}