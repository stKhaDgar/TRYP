package com.rdev.tryp.firebaseDatabase.utils

import com.rdev.tryp.blocks.favourite_drivers.FavoriteDriver

/**
 * Created by Andrey Berezhnoi on 02.04.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


object DatabaseUtils{
    fun arrayContainsDriverId(array: List<FavoriteDriver>, driverId: String?): Boolean {
        return array.any { it.id == driverId }
    }
}