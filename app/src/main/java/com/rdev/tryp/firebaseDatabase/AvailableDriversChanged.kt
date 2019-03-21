package com.rdev.tryp.firebaseDatabase

import com.google.android.gms.maps.model.GroundOverlay
import com.rdev.tryp.firebaseDatabase.model.Driver
import java.util.ArrayList

/**
 * Created by Andrey Berezhnoi on 21.03.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


interface AvailableDriversChanged {
    fun onChanged(drivers: ArrayList<Pair<GroundOverlay, Driver>>)
}