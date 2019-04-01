package com.rdev.tryp.blocks.screens.completedRide

import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rdev.tryp.ContentActivity

import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.payment.utils.PaymentUtils
import kotlinx.android.synthetic.main.fragment_completed_ride.view.*

class CompletedRideFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_completed_ride, container, false)

        initUI(view)

        onClickListener(view)

        return view
    }

    private fun initUI(view: View){
        (activity as? ContentActivity)?.currentFare?.let { price ->
            val fare = price * ConstantsFirebase.TRYP_CAR_FARE
            val textPrice = PaymentUtils.priceToPresentableFormat(fare)
            view.tvFare.text = textPrice

            when {
                textPrice.length <= 2 -> view.tvFare.setTextSize(TypedValue.COMPLEX_UNIT_SP, 92F)
                textPrice.length <= 5 -> view.tvFare.setTextSize(TypedValue.COMPLEX_UNIT_SP, 72F)
                textPrice.length <= 7 -> view.tvFare.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48F)
                else -> view.tvFare.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36F)
            }
        }
    }

    private fun onClickListener(view: View){
        view.back_btn.setOnClickListener {
            (activity as ContentActivity).popBackStack()
            (activity as ContentActivity).startFragment(ContentActivity.TYPE_WRITE_FEEDBACK)
        }

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                (activity as ContentActivity).popBackStack()
                (activity as ContentActivity).startFragment(ContentActivity.TYPE_WRITE_FEEDBACK)
                true
            } else {
                false
            }
        }
    }

}