package com.rdev.tryp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.model.RecentDestination

/**
 * Created by Andrey Berezhnoi on 08.04.2019.
 */


class RecentRidesAdapter(private val itemList: ArrayList<RecentDestination>, private val context: Context) : RecyclerView.Adapter<RecentRidesAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_recent_ride, parent, false))

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]

        holder.name?.text = item.address

        Log.e("DebugRecycler", item.address)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.tvName) as? TextView
    }

}