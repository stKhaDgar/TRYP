package com.rdev.tryp.model.sign_up_response

import com.google.gson.annotations.SerializedName


class Data {

    @SerializedName("message")
    var message: String? = null
    @SerializedName("users")
    var users: Users? = null

    override fun toString(): String {
        return "Data{" +
                "message = '" + message + '\''.toString() +
                ",users = '" + users + '\''.toString() +
                "}"
    }

}