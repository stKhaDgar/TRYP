package com.rdev.tryp.model.status_response

import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("ride")
    var ride: Ride? = null

    override fun toString(): String {
        return "Data{" +
                "ride = '" + ride + '\''.toString() +
                "}"
    }

}