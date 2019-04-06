package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class Location {

    @SerializedName("lng")
    var lng: Double = 0.toDouble()
    @SerializedName("lat")
    var lat: Double = 0.toDouble()

    override fun toString(): String {
        return "Location{" +
                "lng = '" + lng + '\''.toString() +
                ",lat = '" + lat + '\''.toString() +
                "}"
    }

}