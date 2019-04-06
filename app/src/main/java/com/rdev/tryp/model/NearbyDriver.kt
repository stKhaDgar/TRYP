package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class NearbyDriver {

    @SerializedName("data")
    var data: Data? = null

    override fun toString(): String {
        return "NearbyDriver{" +
                "data = '" + data + '\''.toString() +
                "}"
    }

}