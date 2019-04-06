package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class Errors {

    @SerializedName("code_not_found")
    var codeNotFound: String? = null

    override fun toString(): String {
        return "Errors{" +
                "code_not_found = '" + codeNotFound + '\''.toString() +
                "}"
    }

}