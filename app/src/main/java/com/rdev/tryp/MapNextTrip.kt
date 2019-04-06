package com.rdev.tryp

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.rdev.tryp.blocks.invite_friends.InviteFriendsFragment
import com.rdev.tryp.model.RealmUtils
import com.squareup.picasso.Picasso
import androidx.fragment.app.Fragment


class MapNextTrip : Fragment(), View.OnClickListener {

    private var locationBtn: ImageButton? = null
    private var smallImage: ImageView? = null
    private var shareBtn: ImageView? = null
    private var nextBtn: RelativeLayout? = null
    private var tvWelcome: TextView? = null
    private var btnRecentRides: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.next_fragment, container, false)
        initView(v)
        return v
    }

    private fun initView(v: View) {
        smallImage = v.findViewById(R.id.small_image)
        locationBtn = v.findViewById(R.id.location_btn)
        nextBtn = v.findViewById(R.id.next_layout_btn)
        shareBtn = v.findViewById(R.id.share_btn)
        tvWelcome = v.findViewById(R.id.welcome_name)
        btnRecentRides = v.findViewById(R.id.btnRecentRides)

        locationBtn?.setOnClickListener(this)
        nextBtn?.setOnClickListener(this)
        shareBtn?.setOnClickListener(this)
        btnRecentRides?.setOnClickListener(this)

        initUI(v)
    }

    fun initUI(v: View) {
        val user = RealmUtils(activity, null).getCurrentUser()
        val img = user?.image

        if (img != null && img != "null") {
            Picasso.get().load(img).centerCrop().resize(150, 150).into(smallImage)
        } else {
            val height = 100
            val width = 100
            val bitmapDraw = ContextCompat.getDrawable(v.context, R.drawable.small_person) as BitmapDrawable
            val b = bitmapDraw.bitmap
            val bitmap = Bitmap.createScaledBitmap(b, width, height, false)
            smallImage?.setImageBitmap(bitmap)
        }

        val welcomeTemp = if(user?.firstName != null && user.firstName != "null" && user.firstName?.isNotEmpty() == true){
            "Welcome, " + user.firstName + "!"
        } else {
            "Welcome!"
        }
        tvWelcome?.text = welcomeTemp
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.location_btn -> (activity as? ContentActivity)?.zoomToCurrentLocation()
            R.id.next_layout_btn -> (activity as? ContentActivity)?.showDirectionPicker(null)
            R.id.share_btn -> activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.screenContainer, InviteFriendsFragment())
                    ?.addToBackStack("share")
                    ?.commit()
        }
    }
    
}