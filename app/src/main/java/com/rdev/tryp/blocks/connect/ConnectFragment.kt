package com.rdev.tryp.blocks.connect

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.connect.connect_cancel.ConnectCancelFragment
import com.rdev.tryp.firebaseDatabase.model.Driver
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import de.hdodenhof.circleimageview.CircleImageView


@SuppressLint("ValidFragment")
class ConnectFragment @SuppressLint("ValidFragment")
constructor(private val driver: Driver?) : Fragment(), View.OnClickListener {

    private var root:           View? = null
    private var backBtn:        ImageView? = null
    private var cancelButton:   ImageView? = null
    private var supportIv:      ImageView? = null
    private var shareRideIv:    ImageView? = null
    private var supportTv:      TextView? = null
    private var shareRideTv:    TextView? = null
    private var driverName:     TextView? = null
    private var carNum:         TextView? = null
    private var rating:         TextView? = null
    private var plateNumber:    TextView? = null
    private var tvTime:         TextView? = null
    private var contactCv:      CardView? = null
    private var timeLayout:     CardView? = null
    private var driverIv:       CircleImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_connect, container, false)

        initView()

        return root
    }

    private fun initView() {
        backBtn         =   root?.findViewById(R.id.back_btn)
        cancelButton    =   root?.findViewById(R.id.btnCancel)
        shareRideIv     =   root?.findViewById(R.id.share_ride_iv)
        shareRideTv     =   root?.findViewById(R.id.share_ride_tv)
        supportIv       =   root?.findViewById(R.id.support_iv)
        supportTv       =   root?.findViewById(R.id.support_tv)
        contactCv       =   root?.findViewById(R.id.contact_cv)
        driverName      =   root?.findViewById(R.id.driver_name_tv)
        carNum          =   root?.findViewById(R.id.car_num_tv)
        driverIv        =   root?.findViewById(R.id.driver_iv)
        rating          =   root?.findViewById(R.id.tv_rating)
        plateNumber     =   root?.findViewById(R.id.tv_plate_number)
        timeLayout      =   root?.findViewById(R.id.time_layout)
        tvTime          =   root?.findViewById(R.id.tv_time)

        backBtn?.setOnClickListener(this)
        cancelButton?.setOnClickListener(this)
        shareRideTv?.setOnClickListener(this)
        shareRideIv?.setOnClickListener(this)
        supportTv?.setOnClickListener(this)
        supportIv?.setOnClickListener(this)
        contactCv?.setOnClickListener(this)

        driver?.vehicle?.plateNumber?.let { plateText -> carNum?.text = plateText }

        val tempName = "${driver?.firstName} ${driver?.lastName}"
        plateNumber?.text = tempName
        driverName?.text = tempName
        rating?.text = driver?.rating.toString()
        Glide.with(context).load(driver?.image).into(driverIv)
    }

    fun setTime(time: String) {
        if (timeLayout?.visibility == View.GONE) {
            timeLayout?.visibility = View.VISIBLE

            val anim = AnimationUtils.loadAnimation(context, R.anim.slide_from_right)
            timeLayout?.startAnimation(anim)
        }
        tvTime?.text = time
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_btn -> {
                (activity as ContentActivity).popBackStack()
                (activity as ContentActivity).clearMap()
                (activity as ContentActivity).initMap()
            }
            R.id.btnCancel -> activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.screenContainer, ConnectCancelFragment())
                    ?.addToBackStack("connect_cancel")
                    ?.commit()
            R.id.share_ride_iv, R.id.share_ride_tv -> Toast.makeText(context, "share ride", Toast.LENGTH_SHORT).show()
            R.id.support_iv, R.id.support_tv -> Toast.makeText(context, "support", Toast.LENGTH_SHORT).show()
            R.id.contact_cv -> Toast.makeText(context, "Contact", Toast.LENGTH_SHORT).show()
        }
    }

}