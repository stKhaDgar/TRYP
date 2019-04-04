package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Andrey Berezhnoi on 20.03.2019.
 */


@IgnoreExtraProperties
class AvailableDriver{
    var id: String? = null
    var location: Location? = null
}