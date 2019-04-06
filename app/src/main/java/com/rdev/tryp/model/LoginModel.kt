package com.rdev.tryp.model


class LoginModel(number: UserPhoneNumber) {

    var verification_code: String? = null
    var dialing_code: String? = null
    var phone_number: String? = null
    var type = "LOGIN"

    init {
        this.dialing_code = number.dialing_code
        this.phone_number = number.phone_number
    }

}
