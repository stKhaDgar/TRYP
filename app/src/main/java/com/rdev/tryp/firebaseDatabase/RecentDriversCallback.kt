package com.rdev.tryp.firebaseDatabase

import com.rdev.tryp.firebaseDatabase.model.RecentDestination

/**
 * Created by Andrey Berezhnoi on 08.04.2019.
 */


interface RecentDriversCallback{
    fun onUpdated(list: ArrayList<RecentDestination>)
}