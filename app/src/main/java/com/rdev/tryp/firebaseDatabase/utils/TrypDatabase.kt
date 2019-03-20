package com.rdev.tryp.firebaseDatabase.utils

import android.animation.ValueAnimator
import android.os.Handler
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.firebaseDatabase.model.Driver
import java.util.ArrayList

/**
 * Created by Andrey Berezhnoi on 20.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */


class TrypDatabase{

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val const = ConstantsFirebase

    fun initializeAvailableDrivers(map: GoogleMap){

        database.reference.child(const.AVAILABLE_DRIVERS).addChildEventListener(object : ChildEventListener {
            private var drivers = ArrayList<Pair<GroundOverlay, Driver>>()

            init {
                map.setOnCameraMoveListener {
                    val zoom = map.cameraPosition.zoom
                    for((marker, _) in drivers){
                        if(zoom > 10){
                            marker.setDimensions(Math.pow(2.5, (20 - zoom).toDouble()).toFloat() + 40)
                        }
                    }
                }
            }
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                Log.e(const.TAG, "onChildAdded")
                val item = dataSnapshot.getValue(Driver::class.java)
                Log.e(const.TAG, "Driver | id:${item?.id} , lat:${item?.location?.lat} , lon:${item?.location?.lon}")
                item?.location?.lat?.let { first ->
                    item.location?.lon?.let { second ->
                        val marker = map.addGroundOverlay(GroundOverlayOptions().position(LatLng(first, second), 200f).
                                image(BitmapDescriptorFactory.fromResource(R.drawable.marker_car)))
                        drivers.add(Pair(marker, item))
                    }
                }

                Log.e(const.TAG, drivers.size.toString())
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                Log.e(const.TAG, "onChildChanged")
                val item = dataSnapshot.getValue(Driver::class.java)
                for((marker, driver) in drivers){
                    if(driver.id == item?.id){
                        item?.location?.lat?.let { first ->
                            item.location?.lon?.let { second ->

                                val vaLat = ValueAnimator.ofFloat(driver.location?.lat.toString().toFloat(), first.toFloat())
                                val vaLon = ValueAnimator.ofFloat(driver.location?.lon.toString().toFloat(), second.toFloat())

                                vaLat.addUpdateListener { animation ->
                                    val value = animation.animatedValue as Float
                                    marker.position = LatLng(value.toDouble(), driver.location?.lon.toString().toDouble())
                                    driver.location?.lat = value.toDouble()
                                }

                                vaLon.addUpdateListener { animation ->
                                    val value = animation.animatedValue as Float
                                    marker.position = LatLng(driver.location?.lat.toString().toDouble(), value.toDouble())
                                    driver.location?.lon = value.toDouble()
                                }

                                vaLat.start()
                                vaLon.start()
                            }
                        }
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.e(const.TAG, "onChildRemoved")

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                Log.e(const.TAG, "onChildMoved")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(const.TAG, "onCancelled")

            }
        })
    }
}