package com.rdev.tryp.blocks.screens.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rdev.tryp.R

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Alexey Matrosov on 03.03.2019.
 */


class NotificationsFragment : Fragment() {

    private val fakeData = arrayListOf(
            NotificationItem.createPromoNotification("2 days ago", R.drawable.test_promo, "Enjoy our lowest Fares", "Use code TRAVEL 250 to get flat Rs. 250 off on your first Outstation Booking.", "TRAVEL 250"),
            NotificationItem.createNormalNotification("10min ago", R.drawable.test_friend_icon, "Bob Swanson", "I am waiting"),
            NotificationItem.createNormalNotification("20min ago", R.drawable.test_friend_icon, "Bob Swanson", "I am waiting"),
            NotificationItem.createNormalNotification("30min ago", 0, "Bob Swanson", "Hi, I'm here :)")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        val items = fakeData

        val recyclerView = view.findViewById<RecyclerView>(R.id.notifications_container)
        recyclerView.adapter = NotificationsAdapter(context, items)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

}