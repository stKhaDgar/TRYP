package com.rdev.tryp.utils

import android.util.Log

import com.google.android.gms.maps.model.LatLng


interface BearingInterpolator {

    fun interpolate(fraction: Float, lastBearing: Float, from: LatLng, to: LatLng): Float

    class Degree : BearingInterpolator {
        override fun interpolate(fraction: Float, lastBearing: Float, from: LatLng, to: LatLng): Float {
            // http://en.wikipedia.org/wiki/Slerp
            Log.d("tag", "fraction$fraction")
            val deduction = (angleFromCoordinate(from.latitude, from.longitude, to.latitude, to.longitude) - lastBearing).toFloat()
            return lastBearing + deduction * fraction
        }

        private fun angleFromCoordinate(lat1: Double, long1: Double, lat2: Double,
                                        long2: Double): Double {

            val pi = 3.14159
            val latFirst = lat1 * pi / 180
            val longFirst = long1 * pi / 180
            val latSecond = lat2 * pi / 180
            val longSecond = long2 * pi / 180

            val dLon = longSecond - longFirst

            val y = Math.sin(dLon) * Math.cos(latSecond)
            val x = Math.cos(latFirst) * Math.sin(latSecond) - (Math.sin(latFirst)
                    * Math.cos(latSecond) * Math.cos(dLon))

            var bang = Math.atan2(y, x)

            bang = Math.toDegrees(bang)
            bang = (bang + 90) % 360

            return bang
        }
    }

}