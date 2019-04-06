package com.rdev.tryp.model.ride_responce

import android.location.Address

import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.model.DriversItem
import com.rdev.tryp.network.Utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class RequestRideBody(from: Address, destination: Address, isAsap: Boolean, driver: Driver, var user_id: Int) {

    private var to_lat: String? = null
    private var driver_id: String? = null
    private var from_lng: String? = null
    var from: String? = null
    private var to_lng: String? = null
    var to: String? = null
    private var pickup_time: String? = null
    private var ip_address: String? = null
    var asap: String? = null
    private var from_lat: String? = null

    private val max_luggage: Int
    private val max_passenger: Int
    private val type: String?
    private val category: String?
    private val fare: Double

    private val currentDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().time)

    init {
        from_lng = formatLocation(from.longitude)
        from_lat = formatLocation(from.latitude)
        to_lng = formatLocation(destination.longitude)
        to_lat = formatLocation(destination.latitude)
        asap = if (isAsap) "1" else "0"
        this.from = getFullAddress(from)
        to = getFullAddress(destination)
        max_luggage = driver.maxLuggage
        max_passenger = driver.maxPassenger
        type = driver.type
        category = driver.category
        fare = driver.fare
        pickup_time = currentDate
        ip_address = Utils.getIPAddress()
    }

    override fun toString(): String {
        return "RequideRestBody{" +
                "to_lat = '" + to_lat + '\''.toString() +
                ",driver_id = '" + driver_id + '\''.toString() +
                ",from_lng = '" + from_lng + '\''.toString() +
                ",user_id = '" + user_id + '\''.toString() +
                ",from = '" + from + '\''.toString() +
                ",to_lng = '" + to_lng + '\''.toString() +
                ",to = '" + to + '\''.toString() +
                ",pickup_time = '" + pickup_time + '\''.toString() +
                ",ip_address = '" + ip_address + '\''.toString() +
                ",asap = '" + asap + '\''.toString() +
                ",from_lat = '" + from_lat + '\''.toString() +
                "}"
    }

    private fun getFullAddress(address: Address): String {
        val builder = StringBuilder()

        if (address.thoroughfare != null) {
            if (address.subThoroughfare != null)
                builder.append(address.subThoroughfare).append(" ")
            builder.append(address.thoroughfare)
        } else if (address.featureName != null) {
            builder.append(address.featureName)
        }

        builder.append(", ").append(address.locality)
        builder.append(", ").append(address.adminArea)
        builder.append(", ").append(address.countryName)

        return builder.toString()
    }

    private fun formatLocation(location: Double): String {
        val nf = NumberFormat.getNumberInstance(Locale.US)
        val df = nf as DecimalFormat
        df.applyPattern("0.0###")
        return df.format(location)
    }

}
