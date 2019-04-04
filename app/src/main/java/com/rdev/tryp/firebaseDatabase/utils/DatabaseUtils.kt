package com.rdev.tryp.firebaseDatabase.utils

import com.rdev.tryp.blocks.favourite_drivers.FavoriteDriver

/**
 * Created by Andrey Berezhnoi on 02.04.2019.
 */


object DatabaseUtils{

    fun arrayContainsDriverId(array: List<FavoriteDriver>, driverId: String?) = array.any { it.id == driverId }

}