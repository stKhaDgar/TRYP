package com.rdev.tryp.firebaseDatabase

import com.rdev.tryp.firebaseDatabase.model.Ride

/**
 * Created by Andrey Berezhnoi on 22.03.2019.
 */


interface DriverApproveListener{
    fun isApproved(ride: Ride?)
    fun statusChanged(status: Int, ride: Ride)
    fun timeUpdated(time: String)
    fun isDeclined()
}