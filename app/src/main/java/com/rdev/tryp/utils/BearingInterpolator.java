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
            double PI = 3.14159;
            double latFirst = lat1 * PI / 180;
            double longFirst = long1 * PI / 180;
            double latSecond = lat2 * PI / 180;
            double longSecond = long2 * PI / 180;

            double dLon = (longSecond - longFirst);

            double y = Math.sin(dLon) * Math.cos(latSecond);
            double x = Math.cos(latFirst) * Math.sin(latSecond) - Math.sin(latFirst)
                    * Math.cos(latSecond) * Math.cos(dLon);

            double brng = Math.atan2(y, x);

            brng = Math.toDegrees(brng);
            brng = (brng + 90) % 360;

            return brng;
        }
    }
}
