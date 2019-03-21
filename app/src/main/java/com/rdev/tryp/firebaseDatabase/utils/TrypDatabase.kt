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
import com.rdev.tryp.firebaseDatabase.model.Client
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.model.login_response.Users
import java.util.ArrayList
import java.util.HashMap


/**
 * Created by Andrey Berezhnoi on 20.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */


class TrypDatabase{

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val const = ConstantsFirebase
    public val drivers = ArrayList<Pair<GroundOverlay, Driver>>()
    private var listener: AvailableDriversChanged? = null

    fun initializeAvailableDrivers(map: GoogleMap){

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
                val item = dataSnapshot.getValue(Driver::class.java)
                Log.e(const.TAG, "Driver | id:${item?.id} , lat:${item?.location?.lat} , lon:${item?.location?.lng}")
                item?.location?.lat?.let { first ->
                    item.location?.lng?.let { second ->
                        val marker = map.addGroundOverlay(GroundOverlayOptions().position(LatLng(first, second), 200f).
                                image(BitmapDescriptorFactory.fromResource(R.drawable.marker_car)))
                        marker.transparency = 0.0F
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
                val item = dataSnapshot.getValue(Driver::class.java)
                for((marker, driver) in drivers){
                    if(driver.id == item?.id){
                        item?.location?.lat?.let { first ->
                            item.location?.lng?.let { second ->

                                val vaLat = ValueAnimator.ofFloat(driver.location?.lat.toString().toFloat(), first.toFloat())
                                val vaLon = ValueAnimator.ofFloat(driver.location?.lng.toString().toFloat(), second.toFloat())

                                vaLat.addUpdateListener { animation ->
                                    val value = animation.animatedValue as Float
                                    marker.position = LatLng(value.toDouble(), driver.location?.lng.toString().toDouble())
                                    driver.location?.lat = value.toDouble()
                                }

                                vaLon.addUpdateListener { animation ->
                                    val value = animation.animatedValue as Float
                                    marker.position = LatLng(driver.location?.lat.toString().toDouble(), value.toDouble())
                                    driver.location?.lng = value.toDouble()
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
                val item = dataSnapshot.getValue(Driver::class.java)
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

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temp = Client(user.userId.toString(), user.firstName, user.lastName, "none", 5.0F)
                val map = HashMap<String, Client>()
                map[user.userId.toString()] = temp
                clients.setValue(map)
            }

            override fun onCancelled(dataSnapshot: DatabaseError) {}
        })
    }

    private fun setTransparency(from: Float, to: Float, listener: ValueAnimator.AnimatorUpdateListener){
        val anim = ValueAnimator.ofFloat(to, from)
        anim.duration = 1000

        anim.addUpdateListener(listener)
        anim.start()
    }

    fun setAvailableDrivers(listener: AvailableDriversChanged){
        this.listener = listener
    }
}