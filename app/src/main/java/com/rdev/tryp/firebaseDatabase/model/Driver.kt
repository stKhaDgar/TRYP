package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Andrey Berezhnoi on 21.03.2019.
 */


@IgnoreExtraProperties


class Driver {
    var maxLuggage: Int = 0

    var fare: Double = 0.toDouble()

    var maxPassenger: Int = 0

    var category: String? = null

    var type: String? = null

    var vehicle: Vehicle? = null

    var driverId: String? = null

    var dialingCode: String? = null

    var image: String? = null

    var rating: Int = 0

    var lastName: String? = null

    var phoneNumber: String? = null

    var firstName: String? = null

}