package com.rdev.tryp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.util.DirectionConverter
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.libraries.places.api.Places
import com.google.android.material.navigation.NavigationView
import com.rdev.tryp.blocks.connect.ConnectFragment
import com.rdev.tryp.blocks.favourite_drivers.FavouriteDriversFragment
import com.rdev.tryp.blocks.forme.ProfileFragment
import com.rdev.tryp.blocks.invite_friends.InviteFriendsFragment
import com.rdev.tryp.blocks.reward_profile.RewardPointsFragment
import com.rdev.tryp.blocks.reward_profile.RewardProfileFragment
import com.rdev.tryp.blocks.reward_profile.RewardWithdrawFragment
import com.rdev.tryp.blocks.screens.completedRide.CompletedRideFragment
import com.rdev.tryp.blocks.screens.completedRide.FeedbackDriverFragment
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.firebaseDatabase.model.Ride
import com.rdev.tryp.firebaseDatabase.utils.TrypDatabase
import com.rdev.tryp.intro.IntroActivity
import com.rdev.tryp.intro.manager.AccountManager
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.model.TripPlace
import com.rdev.tryp.payment.AddCardFragment
import com.rdev.tryp.payment.PaymentFragment
import com.rdev.tryp.trip.TripFragment
import com.rdev.tryp.trip.detailFragment.DetailHostFragment
import com.rdev.tryp.trip.tryp_car.TrypCarHostFragment
import com.rdev.tryp.blocks.screens.help.HelpFragment
import com.rdev.tryp.blocks.screens.invite2.Invite2Fragment
import com.rdev.tryp.blocks.screens.invite3.Invite3Fragment
import com.rdev.tryp.blocks.screens.legal.LegalFragment
import com.rdev.tryp.blocks.screens.notifications.NotificationsFragment
import com.rdev.tryp.blocks.screens.recap.RecapFragment
import com.rdev.tryp.utils.CurrentLocation
import com.rdev.tryp.utils.Utils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

import java.io.IOException
import java.util.Locale
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rdev.tryp.blocks.trip_history.TripHistoryFragment
import com.rdev.tryp.utils.LocationUpdatedListener
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import io.realm.Realm

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */


class ContentActivity : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {

    companion object {

        const val TYPE_VIEWER = 2

        const val IS_EDIT_CARD = "is_edit_card"

        const val TYPE_RECAP = 0
        const val TYPE_LEGAL = 1
        const val TYPE_INVITE1 = 2
        const val TYPE_INVITE2 = 3
        const val TYPE_INVITE3 = 4
        const val TYPE_HELP = 5
        const val TYPE_NOTIFICATIONS = 6
        const val TYPE_PAYMENT = 8
        const val TYPE_REWARDS = 9
        const val TYPE_FAVORITE = 10
        const val TYPE_REWARD_POINTS = 11
        const val TYPE_PAYMENT_NEW_ENTRY = 12
        const val TYPE_CONNECT = 13
        const val TYPE_REWARDS_WITHDRAW = 14
        const val TYPE_RIDE_COMPLETED = 15
        const val TYPE_WRITE_FEEDBACK = 16
        const val TYPE_TRIP_HISTORY = 17

        private const val REQUEST_CHECK_SETTINGS = 1001

        private const val TAG = "ConnectActivity"

        //route
        var tripFrom: TripPlace? = null
        var tripTo: TripPlace? = null
        var driver: Driver? = null
    }

    var database = TrypDatabase()
    internal var isLocationFound = false
    var b: Bundle? = null
    lateinit var mMap: GoogleMap
    lateinit var fm: FragmentManager
    var pickUpLocation: LatLng? = null

    internal var type = 2
    private var currentLocate: String? = null
    private var pickAddressMarker: Marker? = null
    var myCurrentLocationMarker: Marker? = null
    private var currentCar: GroundOverlay? = null
    var currentFare = 0f
        private set
    var currentRide: Ride? = null

    lateinit var currentLocation: CurrentLocation
    var currentAddress: String? = null

    private var currentPosMarker: Marker? = null

    internal var route: Polyline? = null

    private var mDrawerLayout: DrawerLayout? = null
    private lateinit var navigationView: NavigationView
    lateinit var listener: NavigationView.OnNavigationItemSelectedListener

    private val mLocationListener: LocationListener = object : LocationListener {
        @SuppressLint("MissingPermission")
        override fun onLocationChanged(location: Location) {
            Log.d("tag", "onLocation changed")
            try {
                if (!isLocationFound) {
                    updateCurrentLocation(location)
                }
            } catch (e: NullPointerException) {
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
        override fun onProviderEnabled(s: String) {}
        override fun onProviderDisabled(s: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        Realm.init(this@ContentActivity)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        currentLocation = CurrentLocation(this@ContentActivity, this@ContentActivity)
        currentLocation.init()

        initMenu()
        initMap()
    }

    fun initMap() {
        val intent = intent

        Places.initialize(applicationContext, "AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o")
        fm = supportFragmentManager
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        val fragment: Fragment
        val intentTag = intent.getStringExtra("tag")
        when (intentTag) {
            "tryp" -> fragment = TripFragment()
            "car" -> fragment = TrypCarHostFragment()
            "detail" -> fragment = DetailHostFragment()
            "confirm" -> fragment = ConfirmFragment()
            "set" -> fragment = SetLocationFragment()
            "connect" -> fragment = ConnectFragment(driver)
            else -> fragment = MapNextTrip()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("detail")
                .commit()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isCompassEnabled = false
        database.initializeAvailableDrivers(mMap)
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style))

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            } else {
                checkLocationInSettings()
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            checkLocationInSettings()
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkLocationInSettings() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnFailureListener(this) { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(this@ContentActivity, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
        
        task.addOnSuccessListener(this) {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            var location: Location? = null
            if (gpsEnabled)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (networkEnabled)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            location?.let { loc -> updateCurrentLocation(loc) }

            locationManager.removeUpdates(mLocationListener)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, mLocationListener)
        }
    }

    @Throws(IOException::class)
    internal fun updateCurrentLocation(location: Location) {
        Log.e("LocationUpdate", location.toString())
        val currentPos = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17f))
        if (currentPosMarker != null && currentPosMarker?.isVisible == true) {
            currentPosMarker?.remove()
        }

        val height = 270
        val width = 225
        val bitmapDraw = ContextCompat.getDrawable(this@ContentActivity, R.drawable.current_location_marker) as BitmapDrawable
        val b = bitmapDraw.bitmap
        val markerBitmap = Bitmap.createScaledBitmap(b, width, height, false)

        if (myCurrentLocationMarker == null) {
            myCurrentLocationMarker = mMap.addMarker(MarkerOptions().position(currentPos)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)))
        } else {
            myCurrentLocationMarker?.position = currentPos
        }

        isLocationFound = true
        val geoCoder = Geocoder(this@ContentActivity)
        val addressList = geoCoder.getFromLocation(currentPos.latitude, currentPos.longitude, 1)
        currentLocate = (addressList[0].thoroughfare + " " + addressList[0].locality + ", "
                + addressList[0].adminArea + ", " + addressList[0].countryName)
        Log.d("tag", addressList.toString())
        pickUpLocation = currentPos
    }

    private fun updateCurrentLocation(currentPos: LatLng?) {
        currentPos?.let { pos ->
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 17f))

            if (currentPosMarker != null && currentPosMarker?.isVisible == true) {
                currentPosMarker?.remove()
            }

            isLocationFound = true
            pickUpLocation = pos
        }
    }

    fun popBackStack() {
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            finish()
        }
    }

    fun clearBackStack() {
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    fun showDirectionPicker(destination: TripPlace?) {

        if (Utils.isFragmentInBackStack(supportFragmentManager,
                        ProfileFragment::class.java.name)) {
            supportFragmentManager.popBackStackImmediate(ProfileFragment::class.java.name, 0)
        } else {
            val currentPlace = TripPlace()
            currentPlace.coord = pickUpLocation
            currentPlace.locale = currentLocate

            val fragment = ProfileFragment(currentPlace, destination)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.screenContainer, fragment)
                    .addToBackStack(ProfileFragment::class.java.name)
                    .commit()
        }
    }

    fun onDestinationPicked(startPlace: TripPlace?, destination: TripPlace?, isShowCars: Boolean) {
        if (pickAddressMarker != null && pickAddressMarker?.isVisible == true) {
            pickAddressMarker?.remove()
        }

        if (route != null && route?.isVisible == true) {
            route?.remove()
        }

        if (startPlace != null) {
            pickUpLocation = startPlace.coord
            updateCurrentLocation(startPlace.coord)
        }

        if (pickUpLocation == null) {
            val toast = Toast.makeText(this, "Error. We could not determine your current location", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }

        if (currentCar != null) {
            startPlace?.coord?.latitude?.let { lat ->
                startPlace.coord?.longitude?.let { lng ->
                    currentCar?.position = LatLng(lat, lng)
                }
            }
        }

        val height = 270
        val width = 225
        val bitmapDraw = ContextCompat.getDrawable(this@ContentActivity, R.drawable.destination_marker) as BitmapDrawable
        val b = bitmapDraw.bitmap
        val markerBitmap = Bitmap.createScaledBitmap(b, width, height, false)

        destination?.coord?.let { cord ->
            pickAddressMarker = mMap.addMarker(MarkerOptions().position(cord)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)))
        }

        type = TYPE_VIEWER

        val thread = Thread {
            GoogleDirection.withServerKey("AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o")
                    .from(pickUpLocation)
                    .to(destination?.coord)
                    .transportMode(TransportMode.DRIVING)
                    .execute(object : DirectionCallback {
                        override fun onDirectionSuccess(direction: Direction, rawBody: String) {
                            if (direction.isOK) {
                                for (i in 0 until direction.routeList[0].legList.size) {
                                    val leg = direction.routeList[0].legList[i]
                                    val directionPositionList = leg.directionPoint
                                    if (isShowCars) {
                                        currentFare = (Integer.parseInt(leg.distance.value) / 1000).toFloat()
                                    }
                                    val polylineOptions = DirectionConverter.createPolyline(this@ContentActivity, directionPositionList, 5, Color.BLUE)
                                    route = mMap.addPolyline(polylineOptions)
                                }

                                if (isShowCars) {
                                    fm.beginTransaction()
                                            .replace(R.id.container, TripFragment())
                                            .addToBackStack("dest_pick")
                                            .commit()
                                }

                                val display = windowManager.defaultDisplay
                                val size = Point()
                                display.getSize(size)
                                val padding = 200
                                val width1 = size.x
                                val height1 = size.y

                                /*create for loop/manual to add LatLng's to the LatLngBounds.Builder*/
                                val builder = LatLngBounds.Builder()
                                builder.include(destination?.coord)
                                builder.include(pickUpLocation)

                                /*initialize the padding for map boundary
                            create the bounds from latlngBuilder to set into map camera*/
                                val bounds = builder.build()

                                /*create the camera with bounds and padding to set into map*/
                                val cu = CameraUpdateFactory.newLatLngBounds(bounds, width1, height1 / 2, padding)
                                mMap.animateCamera(cu)

                                tripFrom = startPlace
                                tripTo = destination
                            }
                        }

                        override fun onDirectionFailure(t: Throwable) {
                            // Do something
                        }
                    })
        }

        thread.run()
    }

    fun onDriverRoad(startPlace: TripPlace?, destination: TripPlace) {

        if (pickAddressMarker != null && pickAddressMarker?.isVisible == true) {
            pickAddressMarker?.remove()
        }

        if (route != null && route?.isVisible == true) {
            route?.remove()
        }

        if (startPlace != null) {
            pickUpLocation = startPlace.coord
            if (currentCar == null) {
                updateCurrentLocation(startPlace.coord)
            }
        }

        val height = 270
        val width = 225

        val bitmapDraw = ContextCompat.getDrawable(this@ContentActivity, R.drawable.current_location_marker) as BitmapDrawable
        val b = bitmapDraw.bitmap
        val markerBitmap = Bitmap.createScaledBitmap(b, width, height, false)

        if (myCurrentLocationMarker == null) {
            destination.coord?.let { cord ->
                myCurrentLocationMarker = mMap.addMarker(MarkerOptions().position(cord)
                        .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)))
            }
        }

        type = TYPE_VIEWER

        val thread = Thread {
            GoogleDirection.withServerKey("AIzaSyC41CJUJMGe_9n44zKA0Jk1BAQ_pWp_p1o")
                    .from(pickUpLocation)
                    .to(destination.coord)
                    .transportMode(TransportMode.DRIVING)
                    .execute(object : DirectionCallback {
                        var cameraIsUpdated = false

                        override fun onDirectionSuccess(direction: Direction, rawBody: String) {
                            if (direction.isOK) {
                                for (i in 0 until direction.routeList[0].legList.size) {
                                    val leg = direction.routeList[0].legList[i]
                                    val directionPositionList = leg.directionPoint
                                    val polylineOptions = DirectionConverter.createPolyline(this@ContentActivity, directionPositionList, 5, Color.BLUE)
                                    route = mMap.addPolyline(polylineOptions)
                                    route?.zIndex = 12f
                                }
                                val display = windowManager.defaultDisplay
                                val size = Point()
                                display.getSize(size)
                                val padding = 200
                                val width1 = size.x
                                val height1 = size.y

                                /*create for loop/manual to add LatLng's to the LatLngBounds.Builder*/
                                val builder = LatLngBounds.Builder()
                                builder.include(destination.coord)
                                builder.include(pickUpLocation)

                                /*initialize the padding for map boundary
                            create the bounds from latLngBuilder to set into map camera*/
                                val bounds = builder.build()

                                /*create the camera with bounds and padding to set into map*/
                                if (!cameraIsUpdated) {
                                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, width1, height1 / 2, padding)
                                    mMap.animateCamera(cu)
                                    cameraIsUpdated = true
                                }

                                tripFrom = startPlace
                                tripTo = destination
                            }
                        }

                        override fun onDirectionFailure(t: Throwable) {}
                    })
        }

        thread.run()
    }

    fun openCarsFragments(drivers: List<*>?, currentPos: Int) {

        val fragment = TrypCarHostFragment()
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("demand")
                .commit()
        fragment.setDrivers(drivers, currentPos)

    }

    fun zoomToCurrentLocation() {

        currentLocation.onStartLocationUpdate(object : LocationUpdatedListener{
            override fun locationUpdated(location: Location) {
                Log.e("GeoLocation", "find current Location = ${location.latitude} : ${location.longitude}")
                val currentPos = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 15f))

                val geoCoder = Geocoder(this@ContentActivity, Locale.getDefault())
                var address: String? = null
                try {
                    address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)[0].getAddressLine(0)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                currentAddress = address
                val v = findViewById<ImageView>(R.id.currentLocationMarker)

                val url = RealmUtils(this@ContentActivity, null).getCurrentUser()?.image
                if (url != null && url != "null" && !url.isEmpty()) {
                    Picasso.get().load(url).into(v, object : Callback {
                        override fun onSuccess() {
                            val b = Bitmap.createBitmap(resources.getDimensionPixelOffset(R.dimen.marker_width), resources.getDimensionPixelOffset(R.dimen.marker_height), Bitmap.Config.ARGB_8888)
                            val c = Canvas(b)
                            v.layout(v.left, v.top, v.right, v.bottom)
                            v.draw(c)

                            if (myCurrentLocationMarker != null) {
                                myCurrentLocationMarker?.remove()
                                myCurrentLocationMarker = null
                            }

                            myCurrentLocationMarker = mMap.addMarker(MarkerOptions().position(currentPos)
                                    .icon(BitmapDescriptorFactory.fromBitmap(b)))
                        }

                        override fun onError(e: Exception) {
                            v.setImageResource(R.drawable.default_image)
                            val b = Bitmap.createBitmap(resources.getDimensionPixelOffset(R.dimen.marker_width), resources.getDimensionPixelOffset(R.dimen.marker_height), Bitmap.Config.ARGB_8888)
                            val c = Canvas(b)
                            v.layout(v.left, v.top, v.right, v.bottom)
                            v.draw(c)

                            if (myCurrentLocationMarker != null) {
                                myCurrentLocationMarker?.remove()
                                myCurrentLocationMarker = null
                            }

                            myCurrentLocationMarker = mMap.addMarker(MarkerOptions().position(currentPos)
                                    .icon(BitmapDescriptorFactory.fromBitmap(b)))
                        }
                    })
                } else {
                    v.setImageResource(R.drawable.default_image)
                    val b = Bitmap.createBitmap(resources.getDimensionPixelOffset(R.dimen.marker_width), resources.getDimensionPixelOffset(R.dimen.marker_height), Bitmap.Config.ARGB_8888)
                    val c = Canvas(b)
                    v.layout(v.left, v.top, v.right, v.bottom)
                    v.draw(c)

                    if (myCurrentLocationMarker != null) {
                        myCurrentLocationMarker?.remove()
                        myCurrentLocationMarker = null
                    }

                    myCurrentLocationMarker = mMap.addMarker(MarkerOptions().position(currentPos)
                            .icon(BitmapDescriptorFactory.fromBitmap(b)))
                }
            }
        })
    }

    private fun initMenu() {
        mDrawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.nav_view)

        val menuIcon = findViewById<ImageView>(R.id.menu_icon)
        menuIcon.setOnClickListener(this)

        val user = RealmUtils(this@ContentActivity, null).getCurrentUser()

        val img = user?.image
        if (img != null && img != "null") {
            val iv = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.profile_image)
            Picasso.get().load(img).resize(150, 150).into(iv)
        }

        val tempName = user?.firstName + " " + user?.lastName
        val tvName = navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvName)
        tvName.text = tempName

        listener = NavigationView.OnNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mDrawerLayout?.closeDrawers()

            when (menuItem.itemId) {
                R.id.nav_home -> openMap()
                //                    case R.id.nav_trip_history:
                //                        startFragment(TYPE_TRIP_HISTORY);
                //                        break;

                //                            case R.id.nav_payment:
                //                                //Toast.makeText(getApplicationContext(), "Payment", Toast.LENGTH_SHORT).show();
                //                                startFragment(TYPE_PAYMENT);
                //                                break;
                //                            case R.id.nav_notification:
                //                                Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();
                //                                //startFragment(TYPE_NOTIFICATION);
                //                                break;
                R.id.nav_trip_history -> startFragment(TYPE_TRIP_HISTORY)
                R.id.nav_rewards -> startFragment(TYPE_REWARDS)
                //                            case R.id.nav_emergency_contact:
                //                                Toast.makeText(getApplicationContext(), "Emergency contact", Toast.LENGTH_SHORT).show();
                //                                //startFragment(TYPE_EMERGENCY_CONTACT);
                //                                break;
                //                            case R.id.nav_promotion:
                //                                Toast.makeText(getApplicationContext(), "Promotion", Toast.LENGTH_SHORT).show();
                //                                //startFragment(TYPE_PROMOTION);
                //                                break;
                //                            case R.id.nav_invite_friends:
                //                                Toast.makeText(getApplicationContext(), "Invite Friends", Toast.LENGTH_SHORT).show();
                //                                // startFragment(TYPE_INVITE_FRIENDS);
                //                                break;
                R.id.nav_help -> startFragment(TYPE_HELP)
                R.id.nav_favorite -> startFragment(TYPE_FAVORITE)
                R.id.nav_payment -> startFragment(TYPE_PAYMENT)
                //                            case R.id.nav_about_us:
                //                                Toast.makeText(getApplicationContext(), "About us", Toast.LENGTH_SHORT).show();
                //                                //startFragment(TYPE_ABOUT_US);
                //                                break;
                R.id.nav_logout -> signOut()
            }
            true
        }

        navigationView.setNavigationItemSelectedListener(listener)

        // map menu item checked by default on start
        navigationView.menu.getItem(0).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout?.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun openMap() {
        var count = supportFragmentManager.backStackEntryCount
        while (count != 1) {
            popBackStack()
            count--
        }
    }

    fun goHome() {
        popBackStack()
        //        navigationView.getMenu().getItem(0).setChecked(true);
        //        openMap();
        //        clearMap();
        //        zoomToCurrentLocation();
    }

    fun goHomeOneTransition() {
        navigationView.menu.getItem(0).isChecked = true
        popBackStack()
        clearMap()
        initMap()
        zoomToCurrentLocation()
    }

    fun updateAvatar() {
        val user = RealmUtils(null, null).getCurrentUser()

        val url = user?.image
        val iv = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.profile_image)
        Picasso.get().load(url).centerCrop().resize(200, 200).into(iv)

        val tempName = user?.firstName + " " + user?.lastName
        val tvName = navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvName)
        tvName.text = tempName

        val list = supportFragmentManager.fragments

        for (i in list.indices) {
            if (list[i] is MapNextTrip) {
                list[i].view?.let { v -> (list[i] as MapNextTrip).initUI(v) }
            }
        }

        zoomToCurrentLocation()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    fun openDetailHost(drivers: List<*>?, currentPos: Int) {
        val fragment = DetailHostFragment()
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("detail")
                .commit()
        fragment.setDrivers(drivers, currentPos)
    }

    fun startFragment(type: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (type) {
            TYPE_RECAP -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            RecapFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(RecapFragment::class.java.name, 0)
            } else {
                val fragment = RecapFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(RecapFragment::class.java.name)
                        .commit()
            }
            TYPE_LEGAL -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            LegalFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(LegalFragment::class.java.name, 0)
            } else {
                val fragment = LegalFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(LegalFragment::class.java.name)
                        .commit()
            }
            TYPE_INVITE1 -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            InviteFriendsFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(InviteFriendsFragment::class.java.name, 0)
            } else {
                val fragment = InviteFriendsFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(InviteFriendsFragment::class.java.name)
                        .commit()
            }
            TYPE_INVITE2 -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            Invite2Fragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(Invite2Fragment::class.java.name, 0)
            } else {
                val fragment = Invite2Fragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(Invite2Fragment::class.java.name)
                        .commit()
            }
            TYPE_INVITE3 -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            Invite3Fragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(Invite3Fragment::class.java.name, 0)
            } else {
                val fragment = Invite3Fragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(Invite3Fragment::class.java.name)
                        .commit()
            }
            TYPE_HELP -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            HelpFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(HelpFragment::class.java.name, 0)
            } else {
                val fragment = HelpFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(HelpFragment::class.java.name)
                        .commit()
            }
            TYPE_NOTIFICATIONS -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            NotificationsFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(NotificationsFragment::class.java.name, 0)
            } else {
                val fragment = NotificationsFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(NotificationsFragment::class.java.name)
                        .commit()
            }
            TYPE_FAVORITE -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            FavouriteDriversFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(FavouriteDriversFragment::class.java.name, 0)
            } else {
                val fragment = FavouriteDriversFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(FavouriteDriversFragment::class.java.name)
                        .commit()
            }
            TYPE_TRIP_HISTORY -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            TripHistoryFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(TripHistoryFragment::class.java.name, 0)
            } else {
                val fragment = TripHistoryFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(TripHistoryFragment::class.java.name)
                        .commit()
            }
            TYPE_REWARDS -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            RewardProfileFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(RewardProfileFragment::class.java.name, 0)
            } else {
                val fragment = RewardProfileFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(RewardProfileFragment::class.java.name)
                        .commit()
            }
            TYPE_REWARD_POINTS -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            RewardPointsFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(RewardPointsFragment::class.java.name, 0)
            } else {
                val fragment = RewardPointsFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(RewardPointsFragment::class.java.name)
                        .commit()
            }
            TYPE_PAYMENT_NEW_ENTRY -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            AddCardFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(AddCardFragment::class.java.name, 0)
            } else {
                val fragment = AddCardFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(AddCardFragment::class.java.name)
                        .commit()
            }
            TYPE_PAYMENT -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            PaymentFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(PaymentFragment::class.java.name, 0)
            } else {
                val fragment = PaymentFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(PaymentFragment::class.java.name)
                        .commit()
            }
            TYPE_CONNECT -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            ConnectFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(ConnectFragment::class.java.name, 0)
            } else {
                val fragment = ConnectFragment(driver)
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(ConnectFragment::class.java.name)
                        .commit()
            }
            TYPE_REWARDS_WITHDRAW -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            RewardWithdrawFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(RewardWithdrawFragment::class.java.name, 0)
            } else {
                val fragment = RewardWithdrawFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(RewardWithdrawFragment::class.java.name)
                        .commit()
            }
            TYPE_RIDE_COMPLETED -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            CompletedRideFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(CompletedRideFragment::class.java.name, 0)
            } else {
                val fragment = CompletedRideFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(CompletedRideFragment::class.java.name)
                        .commit()
            }
            TYPE_WRITE_FEEDBACK -> if (Utils.isFragmentInBackStack(supportFragmentManager,
                            FeedbackDriverFragment::class.java.name)) {
                supportFragmentManager.popBackStackImmediate(FeedbackDriverFragment::class.java.name, 0)
            } else {
                val fragment = FeedbackDriverFragment()
                transaction.replace(R.id.screenContainer, fragment)
                        .addToBackStack(FeedbackDriverFragment::class.java.name)
                        .commit()
            }
            //            case TYPE_TRIP_HISTORY:
            //                if (Utils.isFragmentInBackStack(getSupportFragmentManager(),
            //                        ProfileFragment.class.getName())) {
            //                    getSupportFragmentManager().popBackStackImmediate(ProfileFragment.class.getName(), 0);
            //                } else {
            //                    Fragment fragment = new ProfileFragment();
            //                    transaction.replace(R.id.screenContainer, fragment)
            //                            .addToBackStack(ProfileFragment.class.getName())
            //                            .commit();
            //                }
            //                break;
            else -> throw IllegalStateException("Unknown screen type")
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.menu_icon -> mDrawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    private fun signOut() {
        AccountManager.getInstance()?.signOut(this@ContentActivity)
        val intent = Intent(this@ContentActivity, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            if (supportFragmentManager.backStackEntryCount == 2) {
                clearMap()
            }
            super.onBackPressed()
        }
    }

    fun addGroundOverlay(startPlace: TripPlace): GroundOverlay? {
        var go: GroundOverlay? = null

        startPlace.coord?.latitude?.let { lat ->
            startPlace.coord?.longitude?.let { lng ->
                go = mMap.addGroundOverlay(GroundOverlayOptions().position(LatLng(lat, lng), 200f).image(BitmapDescriptorFactory.fromResource(R.drawable.marker_car)))
            }
        }

        mMap.setOnCameraMoveListener {
            val zoom = mMap.cameraPosition.zoom

            go?.setDimensions(Math.pow(2.35, (20 - zoom).toDouble()).toFloat() + 40)
            database.updateZoomDrivers(zoom)
        }

        return go
    }

    fun clearMap() {
        mMap.clear()
        updateCurrentLocation(pickUpLocation)
        zoomToCurrentLocation()
    }

    fun showConnectFragment() {
        openMap()
        popBackStack()
        startFragment(ContentActivity.TYPE_CONNECT)
    }

}