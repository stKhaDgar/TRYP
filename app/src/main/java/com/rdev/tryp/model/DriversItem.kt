package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class DriversItem {

    @SerializedName("max_luggage")
    var maxLuggage: Int = 0

    @SerializedName("fare")
    var fare: Double = 0.toDouble()

    @SerializedName("max_passenger")
    var maxPassenger: Int = 0

    @SerializedName("driver")
    var driver: Driver? = null

    @SerializedName("location")
    var location: Location? = null

    @SerializedName("category")
    var category: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("vehicle")
    var vehicle: Vehicle? = null

    override fun toString(): String {
        return "DriversItem{" +
                "max_luggage = '" + maxLuggage + '\''.toString() +
                ",fare = '" + fare + '\''.toString() +
                ",max_passenger = '" + maxPassenger + '\''.toString() +
                ",driver = '" + driver + '\''.toString() +
                ",location = '" + location + '\''.toString() +
                ",category = '" + category + '\''.toString() +
                ",type = '" + type + '\''.toString() +
                ",vehicle = '" + vehicle + '\''.toString() +
                "}"
    }

}