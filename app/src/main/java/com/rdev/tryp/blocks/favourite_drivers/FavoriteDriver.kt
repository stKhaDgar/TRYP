package com.rdev.tryp.blocks.favourite_drivers

/**
 * Created by Andrey Berezhnoi on 02.04.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


class FavoriteDriver(val id: String?, firstName: String?, lastName: String?, val category: String?, var isLike: Boolean) {
    val title = "$firstName $lastName"
}