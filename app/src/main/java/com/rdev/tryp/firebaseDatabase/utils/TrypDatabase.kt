package com.rdev.tryp.firebaseDatabase.utils

import android.animation.ValueAnimator
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.AvailableDriversChanged
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.firebaseDatabase.DriverApproveListener
import com.rdev.tryp.firebaseDatabase.model.Client
import com.rdev.tryp.firebaseDatabase.model.AvailableDriver
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.firebaseDatabase.model.Ride
import com.rdev.tryp.model.login_response.Users
import com.rdev.tryp.utils.BearingInterpolator
import com.rdev.tryp.utils.CarAnimation
import com.rdev.tryp.utils.LatLngInterpolator
import java.util.ArrayList
import java.util.HashMap


/**
 * Created by Andrey Berezhnoi on 20.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */

class TrypDatabase{

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val const = ConstantsFirebase
    val drivers = ArrayList<Pair<GroundOverlay, AvailableDriver>>()
    private var listener: AvailableDriversChanged? = null

    fun initializeAvailableDrivers(map: GoogleMap){
        drivers.clear()

        database.reference.child(const.AVAILABLE_DRIVERS).addChildEventListener(object : ChildEventListener {

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
                val item = dataSnapshot.getValue(AvailableDriver::class.java)
                Log.e(const.TAG, "Driver | id:${item?.id} , lat:${item?.location?.lat} , lon:${item?.location?.lng}")
                item?.location?.lat?.let { first ->
                    item.location?.lng?.let { second ->
                        val marker = map.addGroundOverlay(GroundOverlayOptions().position(LatLng(first, second), 200f).
                                image(BitmapDescriptorFactory.fromResource(R.drawable.marker_car)))
                        marker.transparency = 0.0F
                        marker.zIndex = 15F
                        drivers.add(Pair(marker, item))
                        setTransparency(0.0F, 1.0F, ValueAnimator.AnimatorUpdateListener { animation ->
                            val value = animation.animatedValue as Float
                            marker.transparency = value
                        })

                        listener?.onChanged(drivers)
                    }
                }

                Log.e(const.TAG, drivers.size.toString())
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                Log.e(const.TAG, "onChildChanged")
                val item = dataSnapshot.getValue(AvailableDriver::class.java)
                for((marker, driver) in drivers){
                    if(driver.id == item?.id){
                        item?.location?.lat?.let { first ->

                            CarAnimation.animateMarkerToGB(marker, item.location, LatLngInterpolator.Spherical(), BearingInterpolator.Degree())

//                            item.location?.lng?.let { second ->
//
//                                driver.location?.lat?.let { driverLat ->
//                                    driver.location?.lng?.let { driverLng ->
//                                        marker.bearing = angleFromCoordinate(driverLat, driverLng, first, second)
//                                    }
//                                }
//
//                                val vaLat = ValueAnimator.ofFloat(driver.location?.lat.toString().toFloat(), first.toFloat())
//                                val vaLon = ValueAnimator.ofFloat(driver.location?.lng.toString().toFloat(), second.toFloat())
//
//                                vaLat.addUpdateListener { animation ->
//                                    val value = animation.animatedValue as Float
//                                    marker.position = LatLng(value.toDouble(), driver.location?.lng.toString().toDouble())
//                                    driver.location?.lat = value.toDouble()
//                                }
//
//                                vaLon.addUpdateListener { animation ->
//                                    val value = animation.animatedValue as Float
//                                    marker.position = LatLng(driver.location?.lat.toString().toDouble(), value.toDouble())
//                                    driver.location?.lng = value.toDouble()
//                                }
//
//                                vaLat.start()
//                                vaLon.start()
//                            }
                        }
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.e(const.TAG, "onChildRemoved")
                val item = dataSnapshot.getValue(AvailableDriver::class.java)
                for((index, pair) in drivers.withIndex()){
                    if(pair.second.id == item?.id){
                        setTransparency(1.0F, 0.0F, ValueAnimator.AnimatorUpdateListener { animation ->
                            val value = animation.animatedValue as Float
                            pair.first.transparency = value
                            if(value == 1.0F){
                                pair.first.remove()
                                drivers.removeAt(index)

                                listener?.onChanged(drivers)
                                return@AnimatorUpdateListener
                            }
                        })
                        return
                    }
                }
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                Log.e(const.TAG, "onChildMoved")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(const.TAG, "onCancelled")
            }
        })
    }

    fun updateUser(user: Users){
        val clients = database.reference.child(const.CLIENTS)
        clients.child(user.userId.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(dataSnapshot: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temp = Client(user.userId.toString(), user.firstName, user.lastName, "none", 5.0F)
                val map = HashMap<String, Client>()
                map[user.userId.toString()] = temp
                clients.setValue(map)
            }
        })
    }

    fun startRide(ride: Ride, driverId: String?, listener: DriverApproveListener){
        val rides = database.reference.child(const.RIDES)
        ride.id?.let { rideId ->
            val currentRide = rides.child(rideId)
            currentRide.setValue(ride)

            driverId?.let { driverId ->
                val driver = database.reference.child(const.DRIVERS).child(driverId)

                driver.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(dataSnapshot.exists()){
                            driver.child("rideId").setValue(rideId)
                        }
                    }
                })
            }

            currentRide.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        dataSnapshot.getValue(Ride::class.java)?.let { item ->
                            if(item.driver?.id == driverId){
                                listener.isApproved(item)
                            }
                        }
                    } else {
                        listener.isDeclined()
                    }
                }
            })
        }
    }

    fun setAvailableDrivers(callback: AvailableDriversChanged.DataChange){
        convertArrayToOnlyDrivers(callback)
        this.listener = object: AvailableDriversChanged{
            override fun onChanged(drivers: ArrayList<Pair<GroundOverlay, AvailableDriver>>) {
                convertArrayToOnlyDrivers(callback)
            }
        }
    }

    private fun convertArrayToOnlyDrivers(callback: AvailableDriversChanged.DataChange){
        val tempArr = ArrayList<Driver>()

        for((_, driver) in drivers){
            database.reference.child(const.DRIVERS).child(driver.id.toString()).addValueEventListener(object : ValueEventListener{
                override fun onCancelled(dataSnapshot: DatabaseError) {}
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.getValue(Driver::class.java)?.let {
                        if(!tempArr.any { field -> field.driverId == it.driverId }){
                            tempArr.add(it)
                        }
                    }
                    callback.onChanged(tempArr)
                }
            })
        }
    }

    fun angleFromCoordinate(lat1: Double, long1: Double, lat2: Double,
                                    long2: Double): Float {
        val dLon = long1 - long2
        val y = Math.sin(dLon) * Math.cos(lat2)
        val x = Math.cos(lat1) * Math.sin(lat2) - (Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon))
        var brng = Math.atan2(x, y)
        brng = Math.toDegrees(brng)
        brng = (brng + 360) % 360
        brng = 180 - brng
        return brng.toFloat()
    }

    private fun setTransparency(from: Float, to: Float, listener: ValueAnimator.AnimatorUpdateListener){
        val anim = ValueAnimator.ofFloat(to, from)
        anim.duration = 1000

        anim.addUpdateListener(listener)
        anim.start()
    }
}