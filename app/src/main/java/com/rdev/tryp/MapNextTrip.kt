package com.rdev.tryp

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.rdev.tryp.blocks.invite_friends.InviteFriendsFragment
import com.rdev.tryp.model.RealmUtils
import com.squareup.picasso.Picasso
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdev.tryp.adapters.RecentRidesAdapter
import com.rdev.tryp.firebaseDatabase.RecentDriversCallback
import com.rdev.tryp.firebaseDatabase.model.RecentDestination


class MapNextTrip : Fragment(), View.OnClickListener {

    private var locationBtn: ImageButton? = null
    private var smallImage: ImageView? = null
    private var shareBtn: ImageView? = null
    private var nextBtn: RelativeLayout? = null
    private var tvWelcome: TextView? = null
    private var btnRecentRides: ImageButton? = null
    private lateinit var cvRecentRides: CardView
    private lateinit var rvRecentRides: RecyclerView
    private var recentRidesIsOpen = false
    private var isFirstOpenedRecentRides = true

    private lateinit var adapter: RecentRidesAdapter
    private val itemList = ArrayList<RecentDestination>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.next_fragment, container, false)
        initView(v)
        initAdapters(v)
        return v
    }

    private fun initAdapters(v: View){
        adapter = RecentRidesAdapter(itemList, v.context)

        rvRecentRides.adapter = adapter
        rvRecentRides.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun initView(v: View) {
        smallImage = v.findViewById(R.id.small_image)
        locationBtn = v.findViewById(R.id.location_btn)
        nextBtn = v.findViewById(R.id.next_layout_btn)
        shareBtn = v.findViewById(R.id.share_btn)
        tvWelcome = v.findViewById(R.id.welcome_name)
        btnRecentRides = v.findViewById(R.id.btnRecentRides)
        cvRecentRides = v.findViewById(R.id.cvRecentRides)
        rvRecentRides = v.findViewById(R.id.rvRecentRides)

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
            R.id.btnRecentRides -> {

                if(isFirstOpenedRecentRides){

                    (activity as ContentActivity).database.getRecentDestinationsRides(object : RecentDriversCallback{
                        override fun onUpdated(list: ArrayList<RecentDestination>) {
                            this@MapNextTrip.itemList.clear()

                            for(item in list){
                                this@MapNextTrip.itemList.add(item)
                            }

                            if(this@MapNextTrip.itemList.size > 3){
                                rvRecentRides.layoutParams.height = view.resources.getDimensionPixelSize(R.dimen.max_height_card_view_recent_rides)
                            }

                            adapter.notifyDataSetChanged()
                        }
                    })

                }

                if(recentRidesIsOpen){

                    val va = ValueAnimator.ofFloat(cvRecentRides.translationY, view.resources.getDimension(R.dimen.height_margin_top_card_recent_rides))

                    va.duration = 600

                    va.addUpdateListener { animation ->
                        val value = animation.animatedValue as Float
                        cvRecentRides.translationY = value
                    }
                    va.start()
                    recentRidesIsOpen = false

                    val padding = view.resources.getDimensionPixelSize(R.dimen.padding6)
                    btnRecentRides?.setPadding(padding, padding, padding, padding)
                    btnRecentRides?.setImageResource(R.drawable.ic_timer)

                } else {

                    val va = ValueAnimator.ofFloat(cvRecentRides.translationY, 0F)

                    va.duration = 600

                    va.addUpdateListener { animation ->
                        val value = animation.animatedValue as Float
                        cvRecentRides.translationY = value
                    }
                    va.start()
                    recentRidesIsOpen = true
                    isFirstOpenedRecentRides = false

                    val padding = view.resources.getDimensionPixelSize(R.dimen.padding10)
                    btnRecentRides?.setPadding(padding, padding, padding, padding)
                    btnRecentRides?.setImageResource(R.drawable.ic_krest)
                }
            }
        }
    }

}