package com.rdev.tryp.model

import com.google.gson.annotations.SerializedName


class Users {

    @SerializedName("dialing_code")
    var dialingCode: String? = null
    @SerializedName("assigned_referral_code")
    var assignedReferralCode: String? = null
    @SerializedName("image")
    var image: String? = null
    @SerializedName("password")
    var password: String? = null
    @SerializedName("last_name")
    var lastName: String? = null
    @SerializedName("phone_number")
    var phoneNumber: String? = null
    @SerializedName("ref_code")
    var refCode: String? = null
    @SerializedName("first_name")
    var firstName: String? = null
    @SerializedName("email")
    var email: String? = null

    override fun toString(): String {
        return "Users{" +
                "dialing_code = '" + dialingCode + '\''.toString() +
                ",assigned_referral_code = '" + assignedReferralCode + '\''.toString() +
                ",image = '" + image + '\''.toString() +
                ",password = '" + password + '\''.toString() +
                ",last_name = '" + lastName + '\''.toString() +
                ",phone_number = '" + phoneNumber + '\''.toString() +
                ",ref_code = '" + refCode + '\''.toString() +
                ",first_name = '" + firstName + '\''.toString() +
                ",email = '" + email + '\''.toString() +
                "}"
    }

}