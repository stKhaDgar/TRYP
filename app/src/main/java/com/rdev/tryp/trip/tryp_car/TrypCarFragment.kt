package com.rdev.tryp.trip.tryp_car

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.payment.utils.PaymentUtils
import com.rdev.tryp.trip.TripFragment
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.rdev.tryp.Constants


@SuppressLint("ValidFragment")
class TrypCarFragment @SuppressLint("ValidFragment")
internal constructor(private val driver: Driver) : Fragment() {

    private var v: View? = null
    private var tvTrypType: TextView? = null
    private var tvNumOfPassengers: TextView? = null
    private var tvNumOfBaggage: TextView? = null
    private var tvNumOfDoor: TextView? = null
    private var tvPrice: TextView? = null
    private var ivCar: ImageView? = null
    private var dialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.tryp_car_fragment, container, false)
        tvTrypType = v?.findViewById(R.id.category_tv)
        ivCar = v?.findViewById(R.id.car_iv)
        tvNumOfDoor = v?.findViewById(R.id.num_of_door_tv)
        tvNumOfBaggage = v?.findViewById(R.id.num_of_baggage)
        tvNumOfPassengers = v?.findViewById(R.id.num_of_passangers)
        tvPrice = v?.findViewById(R.id.price_tv)

        if (driver.vehicle != null) {
            if (driver.vehicle?.image != null) {
                Glide.with(context).load(driver.vehicle?.image).into(ivCar)
            }
        }
        tvTrypType?.text = driver.category
        tvNumOfDoor?.text = "4/4"
        tvNumOfPassengers?.text = "${driver.maxPassenger}"
        tvPrice?.text = PaymentUtils.priceToPresentableFormat((activity as ContentActivity).currentFare * ConstantsFirebase.TRYP_CAR_FARE)
        tvNumOfBaggage?.text = "${driver.maxLuggage}"

        val trypNowBtn = v?.findViewById<CardView>(R.id.tryp_now_btn)
        trypNowBtn?.setOnClickListener { activity?.let { act -> TripFragment.orderTrip(act, driver, ContentActivity.tripFrom, ContentActivity.tripTo) } }

        return v
    }

    private fun showAlertDialod(title: String, message: String) {

        val tvDialogTitle: TextView
        val btnBack: ImageButton
        val tvDialogMessage: TextView
        val v = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null)
        tvDialogMessage = v.findViewById(R.id.dialog_message)
        btnBack = v.findViewById(R.id.dialog_back_btn)
        btnBack.setOnClickListener { dialog?.dismiss() }
        if (dialog != null) {
            dialog?.dismiss()
        }
        tvDialogTitle = v.findViewById(R.id.dialog_title)
        dialog = AlertDialog.Builder(context)
                .setView(v).create()
        dialog?.show()
        tvDialogTitle.text = title
        tvDialogMessage.text = message
    }

    companion object {

        fun getImageByType(type: String) = when (type) {
            Constants.TYPE_TRYP -> R.drawable.tryp_car
            Constants.TYPE_TRYP_ASSIST -> R.drawable.tryp_asist
            Constants.TYPE_TRYP_EXTRA -> R.drawable.tryp_extra
            Constants.TYPE_TRYP_PRIME -> R.drawable.tryp_prime
            else -> null
        }
    }

}