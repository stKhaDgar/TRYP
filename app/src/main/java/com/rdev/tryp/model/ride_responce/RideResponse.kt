package com.rdev.tryp.model.ride_responce

import com.google.gson.annotations.SerializedName


class RideResponse {

    @SerializedName("data")
    var data: Data? = null

    override fun toString(): String {
        return "RideResponse{" +
                "data = '" + data + '\''.toString() +
                "}"
    }

}