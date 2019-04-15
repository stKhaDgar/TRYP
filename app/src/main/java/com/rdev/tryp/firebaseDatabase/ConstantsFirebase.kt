package com.rdev.tryp.firebaseDatabase

/**
 * Created by Andrey Berezhnoi on 20.03.2019.
 */


object ConstantsFirebase {
    const val TAG = "FirebaseDatabase"

    const val AVAILABLE_DRIVERS = "available_drivers"
    const val CLIENTS = "clientsDb"
    const val DRIVERS = "driversDb"
    const val RIDES = "rides"
    const val RIDE_STATUS_PARAM = "status"
    const val RIDE_CANCEL_PARAM = "cancelReason"
    const val FEEDBACKS_ARRAY_PARAM = "feedbacks"
    const val FAVORITES_ARRAY_PARAM = "favorites"
    const val RECENT_DESTINATION_ARRAY_PARAM = "recentDestinations"

    const val STATUS_RIDE_CONFIRMED = 1
    const val STATUS_RIDE_CANCELLED = 2

    const val TRYP_CAR_FARE: Float = 0.8F

    const val STATUS_ROAD_STARTED = 100
    const val STATUS_ROAD_PREPARED = 150
    const val STATUS_ROAD_FINISHED = 200
    const val STATUS_ROAD_CANCELED = 400

}