package com.rdev.tryp.model.status_response

import com.google.gson.annotations.SerializedName


class StatusResponse {

    @SerializedName("data")
    var data: Data? = null

    override fun toString(): String {
        return "StatusResponse{" +
                "data = '" + data + '\''.toString() +
                "}"
    }

}