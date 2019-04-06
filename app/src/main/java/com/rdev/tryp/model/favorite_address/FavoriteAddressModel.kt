package com.rdev.tryp.model.favorite_address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class FavoriteAddressModel {

    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("lat")
    @Expose
    var lat: String? = null
    @SerializedName("lng")
    @Expose
    var lng: String? = null
    @SerializedName("user_id")
    @Expose
    var userId: Int? = null
    @SerializedName("type")
    @Expose
    var type: String? = null

}