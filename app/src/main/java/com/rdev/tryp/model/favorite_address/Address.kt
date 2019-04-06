package com.rdev.tryp.model.favorite_address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Address {

    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("lat")
    @Expose
    var lat: Int? = null
    @SerializedName("lng")
    @Expose
    var lng: Double? = null

}