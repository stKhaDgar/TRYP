package com.rdev.tryp.trip

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.LatLng
import com.rdev.tryp.Constants
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.connect.ConnectFragment
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.firebaseDatabase.DriverApproveListener
import com.rdev.tryp.firebaseDatabase.model.AvailableDriver
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.firebaseDatabase.model.Location
import com.rdev.tryp.firebaseDatabase.model.Ride
import com.rdev.tryp.intro.manager.AccountManager
import com.rdev.tryp.model.DriversItem
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.model.TripPlace
import com.rdev.tryp.model.ride_responce.RequestRideBody
import com.rdev.tryp.model.ride_responce.RideResponse
import com.rdev.tryp.network.ApiClient
import com.rdev.tryp.network.ApiService
import com.rdev.tryp.network.NetworkService
import com.rdev.tryp.utils.BearingInterpolator
import com.rdev.tryp.utils.CarAnimation
import com.rdev.tryp.utils.LatLngInterpolator
import com.rdev.tryp.utils.NotificationHelper

import java.util.ArrayList
import java.util.Locale
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdev.tryp.firebaseDatabase.AvailableDriversChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import io.realm.internal.SyncObjectServerFacade.getApplicationContext

@SuppressLint("ValidFragment")
class TripFragment @SuppressLint("ValidFragment")
constructor() : Fragment(), View.OnClickListener {
    
    private var tripRv: RecyclerView? = null
    private var service: ApiService? = null
    private var tripAdapter: TripAdapter? = null
    private var onDemandCard: CardView? = null
    private var favouriteCard: CardView? = null
    private var tvFavorite: TextView? = null
    private var tvOnDemand: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = ApiClient.getInstance()?.create(ApiService::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.trip_fragment, container, false)
        tripRv = v.findViewById(R.id.trip_recycler_view)
        tripRv?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        onDemandCard = v.findViewById(R.id.on_demand_card)
        onDemandCard?.setOnClickListener(this)
        favouriteCard = v.findViewById(R.id.favourite_card)
        favouriteCard?.setOnClickListener(this)
        tvFavorite = v.findViewById(R.id.favourite_tv)
        tvOnDemand = v.findViewById(R.id.on_demand_tv)
        val backBtn = v.findViewById<ImageView>(R.id.back_btn)
        backBtn.setOnClickListener(this)
        getNearDrivers()
        return v
    }

    private fun getFavouriteDrivers() {

        // set UI
        tvFavorite?.setTextColor(Color.WHITE)
        context?.let { ctx -> tvOnDemand?.setTextColor(ContextCompat.getColor(ctx, R.color.unselect_tv_color)) }
        onDemandCard?.setCardBackgroundColor(Color.WHITE)
        favouriteCard?.setCardBackgroundColor(Color.BLUE)

        val listener = object : TripAdapter.OnItemClickListener{
            override fun onItemClick(item: Any) {
                tripAdapter?.drivers?.indexOf(item)?.let { num -> (activity as? ContentActivity)?.openDetailHost(tripAdapter?.drivers, num) }
            }
        }

        val database = (activity as? ContentActivity)?.database

        database?.setFavoritesDrivers(object : AvailableDriversChanged.DataChange{
            override fun onChanged(drivers: ArrayList<Driver>) {
                context?.let { ctx ->
                    tripAdapter = TripAdapter(drivers, TripAdapter.TYPE_CAR, listener, ctx, (activity as? ContentActivity)?.currentFare)
                }
                tripRv?.adapter = tripAdapter
                tripAdapter?.notifyDataSetChanged()
            }
        })
    }

    private fun getNearDrivers() {

        // set UI
        onDemandCard?.setCardBackgroundColor(Color.BLUE)
        favouriteCard?.setCardBackgroundColor(Color.WHITE)
        tvOnDemand?.setTextColor(Color.WHITE)
        context?.let { ctx -> tvFavorite?.setTextColor(ContextCompat.getColor(ctx, R.color.unselect_tv_color)) }

        val listener = object : TripAdapter.OnItemClickListener{
            override fun onItemClick(item: Any) {
                tripAdapter?.drivers?.indexOf(item)?.let { num -> (activity as ContentActivity).openCarsFragments(tripAdapter?.drivers, num) }
            }
        }

        val database = (activity as? ContentActivity)?.database

        database?.setAvailableDrivers(object : AvailableDriversChanged.DataChange{
            override fun onChanged(drivers: ArrayList<Driver>) {
                if (activity != null) {
                    context?.let { ctx -> tripAdapter = TripAdapter(drivers, TripAdapter.TYPE_CAR, listener, ctx, (activity as? ContentActivity)?.currentFare) }
                }
                tripRv?.adapter = tripAdapter
                tripAdapter?.notifyDataSetChanged()
            }

        })
    }

    private fun filterDrivers(drivers: List<DriversItem>): List<*> {
        val newDrivers = ArrayList<DriversItem>()
        for (d in drivers) {
            if (d.category == Constants.TYPE_TRYP) {
                newDrivers.add(d)
                break
            }
        }
        for (d in drivers) {
            if (d.category == Constants.TYPE_TRYP_ASSIST) {
                newDrivers.add(d)
                break
            }
        }
        for (d in drivers) {
            if (d.category == Constants.TYPE_TRYP_EXTRA) {
                newDrivers.add(d)
                break
            }
        }
        for (d in drivers) {
            if (d.category == Constants.TYPE_TRYP_PRIME) {
                newDrivers.add(d)
                break
            }
        }

        return newDrivers
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.on_demand_card -> getNearDrivers()
            R.id.favourite_card -> getFavouriteDrivers()
            R.id.back_btn -> {
                (activity as ContentActivity).clearMap()
                activity?.onBackPressed()
            }
        }
    }

    companion object {

        fun orderTrip(activity: Activity, driversItem: Driver, start: TripPlace?, end: TripPlace?) {
            val geocoder = Geocoder(activity)
            try {
                var fromAddress: List<Address>? = null
                start?.coord?.latitude?.let { lat ->
                    start.coord?.longitude?.let { lng ->
                        fromAddress = geocoder.getFromLocation(lat, lng, 1)
                    }
                }
                var toAddress: List<Address>? = null
                end?.coord?.latitude?.let { lat ->
                    end.coord?.longitude?.let { lng ->
                        toAddress = geocoder.getFromLocation(lat, lng, 1)
                    }
                }
                ContentActivity.driver = driversItem

                val requestRideBody = RequestRideBody(
                        fromAddress?.get(0),
                        toAddress?.get(0),
                        false,
                        driversItem,
                        AccountManager.getInstance()?.userId)

                val ride = Ride(null,
                        RealmUtils(activity, null).getCurrentUser()?.userId?.toString(),
                        Location(end?.coord?.latitude, end?.coord?.longitude),
                        Location(start?.coord?.latitude, start?.coord?.longitude), null,
                        fromAddress?.get(0)?.getAddressLine(0),
                        toAddress?.get(0)?.getAddressLine(0),
                        (activity as ContentActivity).currentFare)

                NetworkService.getApiService()?.ride_request(requestRideBody)?.enqueue(object : Callback<RideResponse> {
                    override fun onResponse(call: Call<RideResponse>, response: Response<RideResponse>) {
                        val body = response.body()
                        if (body?.data == null) {
                            showAlertDialod("Ride request Failed", "Error at trip order. Please try again", activity)
                        } else {
                            val rideRequest = body.data?.rideRequest
                            showAlertDialod("Ride request Successful", "Your ride request succesffully send", activity)

                            ride.id = rideRequest?.requestId
                            activity.database.startRide(ride, driversItem.driverId, object : DriverApproveListener {
                                var connectIsShown = false
                                var status = 0
                                var currentCar: Pair<GroundOverlay, AvailableDriver>? = null

                                override fun isApproved(ride: Ride?) {
                                    if (status != 0) {
                                        return
                                    }

                                    if (currentCar == null) {
                                        NotificationHelper.approvedFromDriver(getApplicationContext())

                                        ride?.driver?.location?.lat?.let { lat ->
                                            ride.driver?.location?.lng?.let { lng ->
                                                currentCar = Pair<GroundOverlay, AvailableDriver>(activity.addGroundOverlay(TripPlace(Locale.getDefault().displayCountry, LatLng(lat, lng))),
                                                        ride.driver)
                                            }
                                        }

                                        currentCar?.first?.zIndex = 15f

                                        var startPos = LatLng(0.0, 0.0)
                                        var endPos = LatLng(10.0, 10.0)

                                        ride?.driver?.location?.lat?.let { lat ->
                                            ride.driver?.location?.lng?.let { lng ->
                                                startPos = LatLng(lat, lng)
                                            }
                                        }

                                        ride?.pickUpLocation?.lat?.let { lat ->
                                            ride.pickUpLocation?.lng?.let { lng ->
                                                endPos = LatLng(lat, lng)
                                            }
                                        }

                                        activity.onDriverRoad(
                                                TripPlace(Locale.getDefault().displayCountry, startPos),
                                                TripPlace(Locale.getDefault().displayCountry, endPos))

                                    } else {
                                        currentCar?.first?.let { temp -> CarAnimation.animateMarkerToGB(temp, ride?.driver?.location, LatLngInterpolator.Spherical(), BearingInterpolator.Degree()) }
                                    }

                                    if (!connectIsShown) {
                                        activity.showConnectFragment()
                                        connectIsShown = true
                                    }

                                    activity.currentRide = ride
                                }

                                override fun statusChanged(currentStatus: Int, ride: Ride) {
                                    if (currentStatus == ConstantsFirebase.STATUS_ROAD_PREPARED && status == 0) {
                                        status = ConstantsFirebase.STATUS_ROAD_PREPARED

                                        NotificationHelper.statusChanged(getApplicationContext())

                                        showAlertDialod("Driver waiting", "Please get in the car", activity)

                                        Log.e("DebugSome", ride.id)
                                    } else if (currentStatus == ConstantsFirebase.STATUS_ROAD_STARTED && status == 150) {
                                        status = ConstantsFirebase.STATUS_ROAD_STARTED
                                        activity.popBackStack()

                                        NotificationHelper.statusChanged(getApplicationContext())

                                        var startPos = LatLng(0.0, 0.0)
                                        var endPos = LatLng(10.0, 10.0)

                                        ride.driver?.location?.lat?.let { lat ->
                                            ride.driver?.location?.lng?.let { lng ->
                                                startPos = LatLng(lat, lng)
                                            }
                                        }

                                        ride.destinationLocation?.lat?.let { lat ->
                                            ride.destinationLocation?.lng?.let { lng ->
                                                endPos = LatLng(lat, lng)
                                            }
                                        }

                                        activity.onDestinationPicked(
                                                TripPlace(Locale.getDefault().displayCountry, startPos),
                                                TripPlace(Locale.getDefault().displayCountry, endPos),
                                                false)

                                        activity.myCurrentLocationMarker?.isVisible = false

                                        showAlertDialod("Trip started!", "The driver started the trip", activity)

                                    } else if (currentStatus == ConstantsFirebase.STATUS_ROAD_FINISHED && status == 100) {
                                        status = ConstantsFirebase.STATUS_ROAD_FINISHED

                                        NotificationHelper.statusChanged(getApplicationContext())

                                        activity.myCurrentLocationMarker?.isVisible = true
                                        activity.zoomToCurrentLocation()
                                        currentCar = null

                                        activity.startFragment(ContentActivity.TYPE_RIDE_COMPLETED)
                                        activity.database.pushRecentDestination(ride)
                                    }

                                    if (currentCar != null) {
                                        currentCar?.first?.let { temp -> CarAnimation.animateMarkerToGB(temp, ride.driver?.location, LatLngInterpolator.Spherical(), BearingInterpolator.Degree()) }
                                        ride.driver?.location?.lat?.let { lat ->
                                            ride.driver?.location?.lng?.let { lng ->
                                                activity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), activity.mMap.cameraPosition.zoom))
                                            }
                                        }
                                    }
                                }

                                override fun timeUpdated(time: String) {
                                    val list = activity.supportFragmentManager.fragments

                                    for (i in list.indices) {
                                        if (list[i] is ConnectFragment) {
                                            (list[i] as ConnectFragment).setTime(time)
                                        }
                                    }
                                }

                                override fun isDeclined() {
                                    activity.popBackStack()
                                    activity.clearMap()
                                    activity.initMap()
                                }
                            })
                        }
                    }

                    override fun onFailure(call: Call<RideResponse>, t: Throwable) {}
                })

            } catch (ex: Exception) {
                showAlertDialod("Ride request failed", "Error at trip order", activity)
                ex.printStackTrace()
            }
        }

        //    private static void updateStatus(Activity activity, final String requestId) {
        //        NetworkService.getApiService().ride_request_status(AccountManager.getInstance().getUserId(), requestId)
        //                .enqueue(new Callback<StatusResponse>() {
        //            @Override
        //            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
        //                if (response.body().getData().getRide().getVoucherNo() != null) {
        //                    showAlertDialod("Booking successful", "Your booking has been confirmed.\n" +
        //                            "Driver will pickup you in 5 minutes.", activity);
        //                    ((ContentActivity)activity).showConnectFragment();
        //                } else {
        //                    new Handler().postDelayed(new Runnable() {
        //                        @Override
        //                        public void run() {
        //                            updateStatus(activity, requestId);
        //                        }
        //                    }, 3000);
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(Call<StatusResponse> call, Throwable t) {
        //                Log.d("tag", "error");
        //            }
        //        });
        //
        //    }

        private var dialog: AlertDialog? = null

        private fun showAlertDialod(title: String, message: String, context: Context) {
            val dialog_title_tv: TextView
            val back_btn: ImageButton
            val dialog_msg_tv: TextView
            val v = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null)
            dialog_msg_tv = v.findViewById(R.id.dialog_message)
            back_btn = v.findViewById(R.id.dialog_back_btn)


            dialog = AlertDialog.Builder(context)
                    .setView(v).create()

            back_btn.setOnClickListener { view -> dialog?.dismiss() }
            if (dialog != null && title != "The trip is over") {
                dialog?.dismiss()
            } else if (title == "The trip is over") {
                dialog?.dismiss()
                (context as ContentActivity).clearMap()
                context.initMap()
                context.zoomToCurrentLocation()
            }
            dialog_title_tv = v.findViewById(R.id.dialog_title)
            dialog = AlertDialog.Builder(context)
                    .setView(v).create()
            dialog?.show()
            dialog_title_tv.text = title
            dialog_msg_tv.text = message
        }
    }
    
}