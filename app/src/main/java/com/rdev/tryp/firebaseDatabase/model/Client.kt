package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Andrey Berezhnoi on 21.03.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


@IgnoreExtraProperties
class Client(
        var id: String? = null,
        var first_name: String? = null,
        var last_name: String? = null,
        var photo: String? = null,
        var stars: Float? = null
)