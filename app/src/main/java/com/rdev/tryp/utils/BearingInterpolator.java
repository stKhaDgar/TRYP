package com.rdev.tryp.utils;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public interface BearingInterpolator {
    float interpolate(float fraction, float lastbearing, LatLng a, LatLng b);

    class Degree implements BearingInterpolator {
        @Override
        public float interpolate(float fraction, float lastbearing, LatLng from, LatLng to) {
            // http://en.wikipedia.org/wiki/Slerp
            Log.d("tag", "fraction" + fraction);
            float deduction = (float) (angleFromCoordinate(from.latitude, from.longitude, to.latitude, to.longitude) - lastbearing);
            return lastbearing + deduction * fraction;
        }

        private static double angleFromCoordinate(double lat1, double long1, double lat2,
                                                  double long2) {
            double dLon = (long1 - long2);

            double y = Math.sin(dLon) * Math.cos(lat2);
            double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                    * Math.cos(lat2) * Math.cos(dLon);

            double brng = Math.atan2(x, y);

            brng = Math.toDegrees(brng);
            brng = (brng + 360) % 360;
            brng = 180 - brng;
            return brng;
        }
    }
}
