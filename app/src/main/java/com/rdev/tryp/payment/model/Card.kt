package com.rdev.tryp.payment.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.io.Serializable

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 */

@RealmClass
open class Card: RealmObject(), Serializable{
    @Required
    @PrimaryKey
    var id: String? = null
    var type: String? = null
    var number: String? = null
    var expirationMonth: String? = null
    var expirationYear: String? = null
    var cvv: String? = null
}