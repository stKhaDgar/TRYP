package com.rdev.tryp.model.login_response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class VerifySmsResponse {

    @SerializedName("data")
    @Expose
    var data: Data? = null

    override fun toString(): String {
        return "VerifySmsResponse{" +
                "data = '" + data + '\''.toString() +
                "}"
    }
    
}