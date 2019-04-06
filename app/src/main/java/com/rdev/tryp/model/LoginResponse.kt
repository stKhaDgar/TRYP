package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class LoginResponse {

    @SerializedName("data")
    var data: Data? = null

    override fun toString(): String {
        return "LoginResponse{" +
                "data = '" + data + '\''.toString() +
                "}"
    }

}