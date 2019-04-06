package com.rdev.tryp.model


class CreateUser {

    var first_name: String? = null
    var last_name: String? = null
    var email: String? = null
    var country_code: String? = null
    var dialing_code: String? = null
    var phone_number: String? = null
    var ref_code: String? = null

    override fun toString(): String {
        return ("first name " + first_name + "\n"
                + "last name " + last_name + "\n"
                + "email " + email + "\n"
                + "country_code " + country_code + "\n"
                + "dialing_code " + dialing_code + "\n"
                + "phone_number " + phone_number + "\n"
                + "ref_code " + ref_code + "\n")
    }

}
