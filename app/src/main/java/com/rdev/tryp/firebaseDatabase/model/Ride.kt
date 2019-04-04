package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Andrey Berezhnoi on 22.03.2019.
 */


@IgnoreExtraProperties
class Ride(
        var id: String? = null,
        var clientId: String? = null,
        var destinationLocation: Location? = null,
        var pickUpLocation: Location? = null,
        var driver: AvailableDriver? = null,
        var fromAddress: String? = null,
        var toAddress: String? = null,
        var fare: Float? = null
) {
    var status: Int? = null
    var predictedTime: String? = null
}