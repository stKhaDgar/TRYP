package com.rdev.tryp.model.sign_up_response

import com.google.gson.annotations.SerializedName


class SignUpResponse {

    @SerializedName("data")
    var data: Data? = null

    override fun toString(): String {
        return "SignUpResponse{" +
                "data = '" + data + '\''.toString() +
                "}"
    }

}