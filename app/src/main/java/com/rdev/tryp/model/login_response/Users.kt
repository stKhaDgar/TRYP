package com.rdev.tryp.model.login_response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Users : RealmObject() {

    @PrimaryKey
    @SerializedName("user_id")
    @Expose
    var userId: Int? = null
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("dialing_code")
    @Expose
    var dialingCode: String? = null
    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null
    @SerializedName("country_code")
    @Expose
    var countryCode: String? = null
    @SerializedName("assigned_referral_code")
    @Expose
    var assignedReferralCode: String? = null
    @SerializedName("ref_code")
    @Expose
    var refCode: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("favorite_addresses")
    @Expose
    var favoriteAddresses: RealmList<String>? = null

    override fun toString(): String {
        return "Users{" +
                "dialing_code = '" + dialingCode + '\''.toString() +
                ",assigned_referral_code = '" + assignedReferralCode + '\''.toString() +
                ",image = '" + image + '\''.toString() +
                ",user_id = '" + userId + '\''.toString() +
                ",last_name = '" + lastName + '\''.toString() +
                ",phone_number = '" + phoneNumber + '\''.toString() +
                ",ref_code = '" + refCode + '\''.toString() +
                ",first_name = '" + firstName + '\''.toString() +
                ",email = '" + email + '\''.toString() +
                "}"
    }

}