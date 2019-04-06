package com.rdev.tryp.model.favourite_driver

import com.google.gson.annotations.SerializedName


class Driver {

    @SerializedName("dialing_code")
    var dialingCode: String? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("rating")
    var rating: Int = 0

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null

    @SerializedName("first_name")
    var firstName: String? = null

    override fun toString(): String {
        return "Driver{" +
                "dialing_code = '" + dialingCode + '\''.toString() +
                ",image = '" + image + '\''.toString() +
                ",rating = '" + rating + '\''.toString() +
                ",last_name = '" + lastName + '\''.toString() +
                ",phone_number = '" + phoneNumber + '\''.toString() +
                ",first_name = '" + firstName + '\''.toString() +
                "}"
    }

}