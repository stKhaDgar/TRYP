package com.rdev.tryp.trip.detailFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.trip.TripFragment
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment


@SuppressLint("ValidFragment")
class DetailFragment @SuppressLint("ValidFragment")
internal constructor(private val driver: Driver) : Fragment(), View.OnClickListener {
    
    private var tvTrypType: TextView? = null
    private var tvNumOfPassengers: TextView? = null
    private var tvNumOfBaggage: TextView? = null
    private var tvNumOfDoor: TextView? = null
    private var tvPrice: TextView? = null
    private var tvName: TextView? = null
    private var tvCarType: TextView? = null
    private var ivCar: ImageView? = null
    private var ivUser: ImageView? = null
    private var moveCard: CardView? = null
    private var animation: DetailsAnimation? = null
    private var mainRelativeLayout: RelativeLayout? = null
    private var trypNowBtn: CardView? = null
    private var imageCard: CardView? = null

    private val listener: View.OnClickListener? = null
    private var v: View? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.detail_fragment, container, false)
        trypNowBtn = v?.findViewById(R.id.tryp_now_btn)

        tvTrypType = v?.findViewById(R.id.category_tv)
        ivCar = v?.findViewById(R.id.car_iv)
        tvNumOfDoor = v?.findViewById(R.id.num_of_door_tv)
        tvNumOfBaggage = v?.findViewById(R.id.num_of_baggage)
        tvNumOfPassengers = v?.findViewById(R.id.num_of_passangers)
        tvPrice = v?.findViewById(R.id.price_tv)
        tvName = v?.findViewById(R.id.name_tv)
        ivUser = v?.findViewById(R.id.user_iv)
        tvCarType = v?.findViewById(R.id.car_type)
        moveCard = v?.findViewById(R.id.content_move_card)
        mainRelativeLayout = v?.findViewById(R.id.all_content_relative_layout)
        imageCard = v?.findViewById(R.id.image_card)

        driver.vehicle?.let { vehicle -> Glide.with(context).load(vehicle.image).into(ivCar) }

        tvTrypType?.text = driver.category
        tvNumOfDoor?.text = "4/4"
        tvNumOfPassengers?.text = "${driver.maxPassenger}"
        tvPrice?.text = "$${driver.fare}"
        tvNumOfBaggage?.text = "${driver.maxLuggage}"
        tvName?.text = "${driver.firstName} ${driver.lastName}"
        Glide.with(context).load(driver.image).into(ivUser)
        tvCarType?.text = driver.type
        trypNowBtn?.setOnClickListener(this)
        mainRelativeLayout?.setOnClickListener(this)

        animation = DetailsAnimation(moveCard, ivUser, imageCard)

        return v
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tryp_now_btn -> activity?.let { act -> TripFragment.orderTrip(act, driver, ContentActivity.tripFrom, ContentActivity.tripTo) }
            R.id.all_content_relative_layout -> animation?.start()
        }
    }
    
}