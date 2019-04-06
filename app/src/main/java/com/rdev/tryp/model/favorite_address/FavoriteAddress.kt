package com.rdev.tryp.model.favorite_address

import com.google.gson.annotations.SerializedName


class FavoriteAddress {

    @SerializedName("data")
    var data: Data? = null

    override fun toString(): String {
        return "FavoriteAddress{" +
                "data = '" + data + '\''.toString() +
                "}"
    }

}