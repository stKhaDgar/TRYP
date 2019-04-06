package com.rdev.tryp.model.favourite_driver

import com.google.gson.annotations.SerializedName


class FavouriteDriver {

    @SerializedName("data")
    var data: Data? = null

    override fun toString(): String {
        return "FavouriteDriver{" +
                "data = '" + data + '\''.toString() +
                "}"
    }

}