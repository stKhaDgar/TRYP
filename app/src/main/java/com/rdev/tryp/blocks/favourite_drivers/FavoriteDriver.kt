package com.rdev.tryp.blocks.favourite_drivers

/**
 * Created by Andrey Berezhnoi on 02.04.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


class FavoriteDriver(firstName: String?, lastName: String?) {

    var id: String? = null
    val title = "$firstName $lastName"
    var category: String? = null
    var isLike: Boolean = true
    var photo: String? = null

}