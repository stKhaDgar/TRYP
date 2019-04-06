package com.rdev.tryp.model.favourite_driver

import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("drivers")
    var drivers: List<com.rdev.tryp.model.DriversItem>? = null

    override fun toString(): String {
        return "Data{" +
                "drivers = '" + drivers + '\''.toString() +
                "}"
    }

}