package com.rdev.tryp.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rdev.tryp.R
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView


class NavigationAdapter(var listener: NavListener) : RecyclerView.Adapter<NavigationAdapter.DefaultViewHolder>() {
    var data: ArrayList<String> = ArrayList()

    interface NavListener {
        fun onNavigationClick(pos: Int)
    }

    init {
        data.add("Main Flow")
        data.add("Map Tryp")
        data.add("Map Detail")
        data.add("Map Next Tryp")
        data.add("Tryp Later")
        data.add("Tryp Car")
        data.add("Confirm Booking")
        data.add("Set location")
        data.add("Connect")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DefaultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.nav_item, parent, false))

    inner class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTv: TextView = itemView.findViewById(R.id.nav_tv)

        init {
            itemView.setOnClickListener { listener.onNavigationClick(adapterPosition) }
        }
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        holder.itemTv.text = data[position]
    }

    override fun getItemCount() = data.size

}