package com.rdev.tryp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.rdev.tryp.ContentActivity

/**
 * Created by Andrey Berezhnoi on 12.03.2019.
 */

@Suppress("DEPRECATION")
class CurrentLocation(private val context: Context, private val activity: Activity): PlaceSelectionListener, com.google.android.gms.location.LocationListener{
    private var latLng: String? = null
    private var place: String? = null

    private var locationManager: LocationManager? = null
    private var myLL = MyLocationListener()

    private var tagConst = "GeoLocation"

    private var requestLocationCode = 101
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocation: Location? = null
    private var mLocationRequest: LocationRequest? = null
    private val updateInterval = (2 * 1000).toLong()
    private val fastestInterval: Long = 2000

    private var callback: LocationUpdatedListener? = null

    fun init() {
        buildGoogleApiClient()

        mGoogleApiClient?.connect()

    }

    fun onStartLocationUpdate(callbackTemp: LocationUpdatedListener?){
        this.callback = callbackTemp

        if (!checkGPSEnabled()) {
            return
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                checkLocationPermission()
            }
        } else {
            getLocation()
        }
    }

    override fun onLocationChanged(location: Location?) {
        setLocation(location)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

        if (mLocation == null) {
            startLocationUpdates()
        }
        if (mLocation != null) {
            setLocation(mLocation)
        }
    }

    private fun startLocationUpdates() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(updateInterval)
                .setFastestInterval(fastestInterval)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        if(mGoogleApiClient == null || mGoogleApiClient?.isConnected != true){
            return
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)

        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10F, myLL)
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10F, myLL)
    }

    @Synchronized
    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(context, object : GoogleApiClient.ConnectionCallbacks{
            override fun onConnected(p0: Bundle?) {
                Log.e(tagConst, "onConnected()")
                (activity as? ContentActivity)?.zoomToCurrentLocation()
            }

            override fun onConnectionSuspended(p0: Int) {}
        }, GoogleApiClient.OnConnectionFailedListener {})
                .addApi(LocationServices.API)
                .build()

        mGoogleApiClient?.connect()
    }

    private fun checkGPSEnabled(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings") { _, _ ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    context.startActivity(myIntent)
                }
                .setNegativeButton("Cancel") { _, _ -> }
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(context)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK") { _, _ ->
                            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestLocationCode)
                        }
                        .create()
                        .show()

            } else ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestLocationCode)
        }
    }

    private fun setLocation(location: Location?) {

        location?.let { loc -> callback?.locationUpdated(loc) }
    }

    override fun onPlaceSelected(p0: Place) {
        p0.let { pl ->
            latLng = "${pl.latLng?.latitude}, ${pl.latLng?.longitude}"
            place = pl.name.toString()
        }
    }

    override fun onError(p0: Status) {
        Log.e("FilterLocation", p0.statusMessage.toString())
    }

    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location?) {
            Log.e(tagConst, "onLocationChanged()")
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.e(tagConst, "onStatusChanged()")
        }

        override fun onProviderEnabled(provider: String?) {
            Log.e(tagConst, "onProviderEnabled()")
        }

        override fun onProviderDisabled(provider: String?) {
            Log.e(tagConst, "onProviderDisabled()")
        }
    }
}