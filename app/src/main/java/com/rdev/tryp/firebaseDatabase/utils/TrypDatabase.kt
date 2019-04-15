package com.rdev.tryp.firebaseDatabase.utils

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.*
import com.rdev.tryp.firebaseDatabase.model.*
import com.rdev.tryp.model.RealmCallback
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.model.login_response.Users
import com.rdev.tryp.utils.BearingInterpolator
import com.rdev.tryp.utils.CarAnimation
import com.rdev.tryp.utils.LatLngInterpolator
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Andrey Berezhnoi on 20.03.2019.
 */


class TrypDatabase{

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val const = ConstantsFirebase
    val drivers = ArrayList<Pair<GroundOverlay, AvailableDriver>>()
    private var listener: AvailableDriversChanged? = null

    fun initializeAvailableDrivers(map: GoogleMap){
        for(item in drivers){
            item.first.remove()
        }
        drivers.clear()

        database.reference.child(const.AVAILABLE_DRIVERS).addChildEventListener(object : ChildEventListener {

            init {
                map.setOnCameraMoveListener {
                    val zoom = map.cameraPosition.zoom
                    for((marker, _) in drivers){
                        if(zoom > 10){
                            marker.setDimensions(Math.pow(2.3, (20 - zoom).toDouble()).toFloat() + 40)
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
                        CarAnimation.animateMarkerToGB(marker, item?.location, LatLngInterpolator.Spherical(), BearingInterpolator.Degree())
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

    fun updateUser(user: Users, context: Context?, callback: RealmCallback?){
        val clients = database.reference.child(const.CLIENTS)

        clients.child(user.userId.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val client = clients.child(user.userId.toString())

                if(dataSnapshot.exists()){
                    user.image.let { photo -> client.child("photo").setValue(photo) }
                    user.firstName.let { firstName -> client.child("first_name").setValue(firstName) }
                    user.lastName.let { lastName -> client.child("last_name").setValue(lastName) }
                }
            }
        })
    }

    fun getOrCreateUser(user: Users?, context: Context?, callback: RealmCallback?){
        val clients = database.reference.child(const.CLIENTS)

        clients.child(user?.userId.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) { callback?.error() }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val client = clients.child(user?.userId.toString())

                if(dataSnapshot.exists()){
                    val item = dataSnapshot.getValue(Client::class.java)
                    RealmUtils(context, null).updateUser(item)
                } else {
                    val temp = Client(user?.userId.toString(), user?.firstName, user?.lastName, null, 5.0F)
                    client.setValue(temp)
                }

                callback?.dataUpdated()
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
                                var index = -1
                                hey@for(i in 0 until drivers.size){
                                    if(item.driver?.id == drivers[i].second.id){
                                        index = i
                                        drivers[i].first.remove()
                                        break@hey
                                    }
                                }

                                if(index != -1){
                                    drivers.removeAt(index)
                                }

                                listener.isApproved(item)
                            }
                            if(item.status != null){
                                item.status?.let{ status -> listener.statusChanged(status, item) }
                            }
                            item.predictedTime?.let { time -> listener.timeUpdated(time) }
                        }
                    } else {
                        listener.isDeclined()
                    }
                }
            })
        }
    }

    fun getDriver(id: String, callback: AvailableDriversChanged.GetData.Driver?) {
        database.reference.child(const.DRIVERS).child(id).addListenerForSingleValueEvent(object : ValueEventListener{
            var isReceived = false

            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!isReceived){
                    val item = dataSnapshot.getValue(Driver::class.java)
                    callback?.onCompleted(item)
                    isReceived = true
                }
            }
        })
    }

    fun addDriverToFavorite(driverId: String?, isAdd: Boolean){
        val item = database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.FAVORITES_ARRAY_PARAM).child(driverId.toString())

        Log.e("DebugSome", driverId.toString())

        if(isAdd){
            item.setValue(driverId)
        } else {
            item.removeValue()
        }
    }

    fun getFavoritedDrivers(callback: AvailableDriversChanged.GetData.Driver?){

        database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.FAVORITES_ARRAY_PARAM)
                .addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for(postSnapshot in dataSnapshot.children){

                            postSnapshot.getValue(String::class.java)?.let { item ->

                                getDriver(item, callback)

                            }

                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {}

                })
    }

    fun driverIsFavorited(driverId: String, callback: AvailableDriversChanged.GetData.IsFavorite){

        database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.FAVORITES_ARRAY_PARAM)
                .addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        callback.isFavorite(dataSnapshot.children.any { item ->
                            item.getValue(String::class.java) == driverId
                        })
                    }

                    override fun onCancelled(p0: DatabaseError) {}

                })
    }

    fun getRecentDestinationsRides(callback: RecentDriversCallback){
        database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.RECENT_DESTINATION_ARRAY_PARAM)
                .addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val tempArr = ArrayList<RecentDestination>()

                        for(postSnapshot in dataSnapshot.children){
                            postSnapshot.getValue(RecentDestination::class.java)?.let { item ->
                                try {
                                    item.dateCreatedAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(item.createdAt)
                                } catch (e: Exception){}
                                tempArr.add(item)
                            }
                        }

                        tempArr.sortByDescending { it.dateCreatedAt }

                        callback.onUpdated(tempArr)
                    }

                    override fun onCancelled(p0: DatabaseError) {}
                })
    }

    fun pushRecentDestination(ride: Ride) {
        val item = database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.RECENT_DESTINATION_ARRAY_PARAM).push()

        val recentDestination = RecentDestination()

        recentDestination.id = item.key
        recentDestination.address = ride.toAddress
        recentDestination.destinationLocation = ride.destinationLocation
        recentDestination.createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).format(Calendar.getInstance().time)

        item.setValue(recentDestination)
    }

    fun getRecentRides(callback: RecentRidesCallback) {
        database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.RECENT_RIDES_ARRAY_PARAM)
                .addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val tempArr = ArrayList<RecentRide>()

                        for(postSnapshot in dataSnapshot.children){
                            postSnapshot.getValue(RecentRide::class.java)?.let { item ->
                                try {
                                    item.dateCreatedAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(item.createdAt)
                                } catch (e: Exception){}
                                tempArr.add(item)
                            }
                        }

                        tempArr.sortByDescending { it.dateCreatedAt }

                        callback.onUpdated(tempArr)
                    }

                    override fun onCancelled(p0: DatabaseError) {}
                })
    }

    fun sendFeedback(id: String, feedback: Feedback) {
        val item = database.reference.child(const.DRIVERS).child(id).child(const.FEEDBACKS_ARRAY_PARAM).push()
        feedback.id = item.key
        item.setValue(feedback)
    }

    fun cancelRide(id: String?, reason: String?){
        id?.let { field ->
            database.reference.child(const.RIDES).child(field).child(const.RIDE_STATUS_PARAM).setValue(const.STATUS_ROAD_CANCELED)
            reason?.let { reasonTemp ->
                database.reference.child(const.RIDES).child(field).child(const.RIDE_CANCEL_PARAM).setValue(reasonTemp)
            }
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

    fun setFavoritesDrivers(callback: AvailableDriversChanged.DataChange){
        convertArrayToOnlyDrivers(object : AvailableDriversChanged.DataChange{
            override fun onChanged(drivers: ArrayList<Driver>) {
                val tempArr = ArrayList<Driver>()

                database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.FAVORITES_ARRAY_PARAM)
                        .addValueEventListener(object : ValueEventListener{

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for(postSnapshot in dataSnapshot.children){

                                    postSnapshot.getValue(String::class.java)?.let { item ->

                                        drivers.map {
                                            if(it.driverId == item){
                                                tempArr.add(it)
                                            }
                                        }

                                        callback.onChanged(tempArr)
                                    }
                                }
                            }

                            override fun onCancelled(p0: DatabaseError) {}
                        })
            }
        })

        this.listener = object: AvailableDriversChanged{
            override fun onChanged(drivers: ArrayList<Pair<GroundOverlay, AvailableDriver>>) {
                convertArrayToOnlyDrivers(object : AvailableDriversChanged.DataChange{
                    override fun onChanged(drivers: ArrayList<Driver>) {
                        val tempArr = ArrayList<Driver>()

                        database.reference.child(const.CLIENTS).child(RealmUtils(null, null).getCurrentUser()?.userId.toString()).child(const.FAVORITES_ARRAY_PARAM)
                                .addValueEventListener(object : ValueEventListener{

                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for(postSnapshot in dataSnapshot.children){

                                            postSnapshot.getValue(String::class.java)?.let { item ->

                                                drivers.map {
                                                    if(it.driverId == item){
                                                        tempArr.add(it)
                                                    }
                                                }

                                                callback.onChanged(tempArr)
                                            }
                                        }
                                    }

                                    override fun onCancelled(p0: DatabaseError) {}
                                })
                    }

                })
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

    fun updateZoomDrivers(zoom: Float){
        for((marker, _) in drivers){
            if(zoom > 10){
                marker.setDimensions(Math.pow(2.5, (20 - zoom).toDouble()).toFloat() + 40)
            }
        }
    }

    private fun setTransparency(from: Float, to: Float, listener: ValueAnimator.AnimatorUpdateListener){
        val anim = ValueAnimator.ofFloat(to, from)
        anim.duration = 1000

        anim.addUpdateListener(listener)
        anim.start()
    }

}