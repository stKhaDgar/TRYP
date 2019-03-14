package com.rdev.tryp.payment.model

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */

@RealmClass
open class Card: RealmObject(){
    @Required
    var id: String? = null
}