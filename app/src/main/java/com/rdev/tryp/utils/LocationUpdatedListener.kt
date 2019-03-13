package com.rdev.tryp.utils

import android.location.Location

/**
 * Created by Andrey Berezhnoi on 12.03.2019.
 */

interface LocationUpdatedListener{
    fun locationUpdated(location: Location)
}