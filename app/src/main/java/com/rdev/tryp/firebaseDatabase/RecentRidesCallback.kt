package com.rdev.tryp.firebaseDatabase

import com.rdev.tryp.firebaseDatabase.model.RecentRide

/**
 * Created by Andrey Berezhnoi on 15.04.2019.
 */


interface RecentRidesCallback {
    fun onUpdated(ride: RecentRide)

}