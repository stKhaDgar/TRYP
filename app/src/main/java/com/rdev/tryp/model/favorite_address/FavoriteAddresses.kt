package com.rdev.tryp.model.favorite_address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class FavoriteAddresses {

    @SerializedName("work")
    @Expose
    var address: Address? = null

}