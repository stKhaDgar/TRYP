package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("drivers")
    var drivers: List<DriversItem>? = null

    override fun toString(): String {
        return "Data{" +
                "drivers = '" + drivers + '\''.toString() +
                "}"
    }

}