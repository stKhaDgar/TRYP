package com.rdev.tryp.blocks.trip_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.trip_history.past_trip.PastTripsFragment
import kotlinx.android.synthetic.main.fragment_trip_history.view.*


class TripHistoryFragment : Fragment() {

    private lateinit var mViewPager: ViewPager
    private lateinit var mTabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trip_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI(view)
        onClickListener(view)
    }

    private fun initUI(view: View){
        mViewPager = view.trips_pager
        mViewPager.adapter = object : FragmentStatePagerAdapter(activity?.supportFragmentManager) {

            override fun getItem(position: Int): Fragment {
                return if (position == 0) {
                    PastTripsFragment()
                } else {
                    UpcomingTripsFragment()
                }
            }

            override fun getCount() = 2

            override fun getPageTitle(position: Int): CharSequence? {
                if (position == 0) {
                    return resources.getString(R.string.past_trips)
                } else if (position == 1) {
                    return resources.getString(R.string.upcoming_trips)
                }
                return "Undefined"
            }
        }

        mTabLayout = view.trips_tabs
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun onClickListener(view: View){
        view.back_btn.setOnClickListener {
            (activity as ContentActivity).goHome()
        }
    }

}