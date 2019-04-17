package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

/**
 * Created by Andrey Berezhnoi on 15.04.2019.
 */


@IgnoreExtraProperties
class RecentRide {
    var id: String? = null
    var destinationAddress: String? = null
    var destinationLocation: Location? = null
    var fromAddress: String? = null
    var fromLocation: Location? = null
    var createdAt: String? = null
    var dateCreatedAt: Date? = null
    var status: Int? = null
    var driverId: String? = null
    var driver: Driver? = null
    var fare: Float? = null
}