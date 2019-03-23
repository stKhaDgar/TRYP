package com.rdev.tryp.utils;

import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.rdev.tryp.firebaseDatabase.model.Location;

public class CarAnimation {
    static GroundOverlay carMarker;
    static Handler handler;

    public static void animateMarkerToGB(final GroundOverlay marker, final Location finalPosition, final LatLngInterpolator latLngInterpolator, BearingInterpolator bearingInterpolator) {
        carMarker = marker;
        float lastBearing = marker.getBearing();
        final LatLng startPosition = marker.getPosition();
        handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 2000;
        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);
                marker.setPosition(latLngInterpolator.interpolate(v, startPosition, new LatLng(finalPosition.getLat(), finalPosition.getLng())));
                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
                marker.setBearing(bearingInterpolator.interpolate(v, lastBearing, startPosition, new LatLng(finalPosition.getLat(), finalPosition.getLng())));
            }
        });
    }


}
