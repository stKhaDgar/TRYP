package com.rdev.tryp.model

import com.google.android.gms.maps.model.LatLng

class TripPlace {
    var locale: String? = null
    var coord: LatLng? = null

    constructor()

    constructor(locale: String?, coord: LatLng) {
        this.locale = locale
        this.coord = coord
    }
}
