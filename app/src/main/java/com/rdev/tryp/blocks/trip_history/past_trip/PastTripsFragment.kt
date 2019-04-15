package com.rdev.tryp.blocks.trip_history.past_trip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.firebaseDatabase.model.Location
import com.rdev.tryp.firebaseDatabase.model.RecentRide
import kotlinx.android.synthetic.main.fragment_past_trips.view.*
import java.util.*
import kotlin.collections.ArrayList


class PastTripsFragment : Fragment() {

    private lateinit var adapter: PastTripAdapter
    private val mList = ArrayList<RecentRide>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_trips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter(view)
        initTrips(view)
    }

    private fun initAdapter(view: View){
        adapter = PastTripAdapter(view.context, mList)
        view.trips_recycler_view.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        view.trips_recycler_view.adapter = adapter
    }

    private fun initTrips(view: View){
        val ride = RecentRide()
        ride.id = "123"
        ride.fromLocation = Location()
        ride.fromAddress = "From address"
        ride.destinationLocation = Location()
        ride.destinationAddress = "Destination address"
        ride.status = 1
        ride.dateCreatedAt = Calendar.getInstance().time
        ride.driver = Driver()

        mList.add(ride)
    }

}