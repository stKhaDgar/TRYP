package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class Vehicle {

    @SerializedName("image")
    var image: String? = null
    @SerializedName("color")
    var color: String? = null
    @SerializedName("model")
    var model: String? = null
    @SerializedName("plate_number")
    var plateNumber: String? = null
    @SerializedName("make")
    var make: String? = null

    override fun toString(): String {
        return "Vehicle{" +
                "image = '" + image + '\''.toString() +
                ",color = '" + color + '\''.toString() +
                ",model = '" + model + '\''.toString() +
                ",plate_number = '" + plateNumber + '\''.toString() +
                ",make = '" + make + '\''.toString() +
                "}"
    }

}