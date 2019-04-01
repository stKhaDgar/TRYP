package com.rdev.tryp.firebaseDatabase.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Andrey Berezhnoi on 01.04.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */


@IgnoreExtraProperties
class Feedback(
        var id: String? = null,
        var clientId: String? = null,
        var rating: Float? = null,
        var message: String? = null,
        var created_at: String? = null
)