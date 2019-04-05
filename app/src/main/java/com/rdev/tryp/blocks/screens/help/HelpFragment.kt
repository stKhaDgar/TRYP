package com.rdev.tryp.blocks.screens.help

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.helper.BaseFragment


import androidx.annotation.ArrayRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Alexey Matrosov on 04.03.2019.
 */


class HelpFragment : BaseFragment(), View.OnClickListener {

    private val fakeData = listOf(
            RecentTripItem(R.drawable.test_friend_icon, true, "Aug, 11:45 PM", "Cancelled Early", "$2.25", "1.3mi - 7m"),
            RecentTripItem(R.drawable.test_friend_icon, true, "Aug, 11:45 PM", "Cancelled Early", "$2.25", "1.3mi - 7m"),
            RecentTripItem(R.drawable.test_friend_icon, false, "Aug, 11:45 PM", "Cancelled Early", "$2.25", "1.3mi - 7m"))


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)

        val gradient = view.findViewById<View>(R.id.gradient)
        gradient.background = generateGradient(R.array.help_gradient)

        val recyclerView = view.findViewById<RecyclerView>(R.id.help_trips_container)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecentTripsAdapter(context, fakeData)

        val backBtn = view.findViewById<ImageView>(R.id.back_btn)
        backBtn.setOnClickListener(this)

        return view
    }

    private fun generateGradient(@ArrayRes gradient: Int): Drawable {
        val colors = resources.getIntArray(gradient)
        val colorDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
        colorDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        colorDrawable.shape = GradientDrawable.RECTANGLE

        return colorDrawable
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_btn -> (activity as? ContentActivity)?.goHome()
        }
    }

}
