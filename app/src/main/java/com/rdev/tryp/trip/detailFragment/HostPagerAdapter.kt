package com.rdev.tryp.trip.detailFragment

import com.rdev.tryp.firebaseDatabase.model.Driver
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


internal class HostPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private lateinit var drivers: List<*>

    override fun getItem(position: Int): Fragment? {
        return if (drivers.isEmpty()) {
            null
        } else DetailFragment(drivers[position] as Driver)
    }

    fun setDrivers(drivers: List<*>) {
        this.drivers = drivers
        notifyDataSetChanged()
    }

    override fun getCount() = drivers.size

}