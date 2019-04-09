package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Andrey Berezhnoi on 08.04.2019.
 */


@IgnoreExtraProperties
class RecentDestination {
    var id: String? = null
    var address: String? = null
    var destinationLocation: Location? = null
    var createdAt: String? = null
    var dateCreatedAt: Date? = null

}