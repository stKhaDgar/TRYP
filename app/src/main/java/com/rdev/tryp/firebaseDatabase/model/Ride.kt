package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Andrey Berezhnoi on 22.03.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


@IgnoreExtraProperties
class Ride(
        var id: String? = null,
        var destinationLocation: Location? = null,
        var pickUpLocation: Location? = null,
        var driver: AvailableDriver? = null,
        var fromAddress: String? = null,
        var toAddress: String? = null
)