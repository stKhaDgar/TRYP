package com.rdev.tryp.model.favorite_address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("favorite_addresses")
    @Expose
    var favoriteAddresses: FavoriteAddresses? = null

}