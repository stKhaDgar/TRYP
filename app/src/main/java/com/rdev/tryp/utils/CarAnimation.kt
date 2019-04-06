package com.rdev.tryp.utils

import android.os.Handler
import android.os.SystemClock
import android.view.animation.AccelerateDecelerateInterpolator

import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.LatLng
import com.rdev.tryp.firebaseDatabase.model.Location

object CarAnimation {

    private lateinit var carMarker: GroundOverlay
    private lateinit var handler: Handler

    fun animateMarkerToGB(marker: GroundOverlay, finalPosition: Location?, latLngInterpolator: LatLngInterpolator, bearingInterpolator: BearingInterpolator) {
        carMarker = marker
        val lastBearing = marker.bearing
        val startPosition = marker.position
        handler = Handler()
        val start = SystemClock.uptimeMillis()
        val interpolator = AccelerateDecelerateInterpolator()
        val durationInMs = 2000f
        handler.post(object : Runnable {
            var elapsed: Long = 0
            var t: Float = 0.toFloat()
            var v: Float = 0.toFloat()

            override fun run() {
                // Calculate progress using interpolator
                finalPosition?.lat?.let { lat ->
                    finalPosition.lng?.let { lng ->
                        elapsed = SystemClock.uptimeMillis() - start
                        t = elapsed / durationInMs
                        v = interpolator.getInterpolation(t)
                        marker.position = latLngInterpolator.interpolate(v, startPosition, LatLng(lat, lng))
                        // Repeat till progress is complete.
                        if (t < 1) {
                            // Post again 16ms later.
                            handler.postDelayed(this, 16)
                        }
                        marker.bearing = bearingInterpolator.interpolate(v, lastBearing, startPosition, LatLng(lat, lng))
                    }
                }
            }
        })
    }

}