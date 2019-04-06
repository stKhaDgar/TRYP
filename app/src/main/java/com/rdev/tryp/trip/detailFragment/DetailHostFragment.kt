package com.rdev.tryp.trip.detailFragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R

import java.util.ArrayList
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager


class DetailHostFragment : Fragment() {

    private var viewPager: ViewPager? = null
    private var btnBack: ImageButton? = null
    private var drivers: List<*> = ArrayList<Any>()
    private var currentPos: Int = 0
    private var adapter: HostPagerAdapter? = null
    private var v: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.detail_host_fragment, container, false)
        viewPager = v?.findViewById(R.id.detail_viewpager)
        adapter = HostPagerAdapter(childFragmentManager)
        adapter?.setDrivers(drivers)
        viewPager?.adapter = adapter
        viewPager?.currentItem = currentPos
        dpToPx(8)?.let { temp -> viewPager?.pageMargin = temp }
        btnBack = v?.findViewById(R.id.back_btn)
        btnBack?.setOnClickListener { (activity as ContentActivity).popBackStack() }
        return v
    }

    fun setDrivers(drivers: List<*>, currentPos: Int) {
        this.drivers = drivers
        this.currentPos = currentPos
        if (adapter != null) {
            adapter?.setDrivers(drivers)
        }
    }

    private fun dpToPx(dp: Int): Int? {
        val displayMetrics = activity?.resources?.displayMetrics
        return if(displayMetrics != null){
            Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        } else null
    }

}