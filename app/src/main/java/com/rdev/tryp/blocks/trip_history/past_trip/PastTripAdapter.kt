package com.rdev.tryp.blocks.trip_history.past_trip

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.firebaseDatabase.model.RecentRide
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Andrey Berezhnoi on 15.04.2019.
 */


class PastTripAdapter(private val context: Context, private val mList: ArrayList<RecentRide>) : RecyclerView.Adapter<PastTripAdapter.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val inflater = LayoutInflater.from(context).inflate(R.layout.list_item, null)

        return TripsHolder(inflater)
    }

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val model = mList[position]

        Picasso.get().load(model.driver?.image).into(holder.mAvatarImageView)
        holder.mTripFromTextView?.text = model.fromAddress
        holder.mTripToTextView?.text = model.destinationAddress
        model.dateCreatedAt?.let { date -> holder.mDateButton?.text = SimpleDateFormat("d MMM 'at' h:mm a", Locale.ENGLISH).format(date) }
        holder.mNameTextView?.text = model.driver?.firstName

        when(model.status){
            ConstantsFirebase.STATUS_RIDE_CONFIRMED -> {
                holder.mStatusTextView?.setTextColor(ContextCompat.getColor(context, R.color.confirmed))
                holder.mStatusTextView?.text = "Confirmed"
            }
            ConstantsFirebase.STATUS_RIDE_CANCELLED -> {
                holder.mStatusTextView?.setTextColor(ContextCompat.getColor(context, R.color.cancelled))
                holder.mStatusTextView?.text = "Cancelled"
            }
        }

        if(position == 0){
            (holder.cardView?.layoutParams as LinearLayout.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.dimen24)
        } else if (position == mList.size-1) {
            (holder.cardView?.layoutParams as LinearLayout.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.dimen2)
            (holder.cardView.layoutParams as LinearLayout.LayoutParams).bottomMargin = context.resources.getDimensionPixelSize(R.dimen.dimen24)
        } else {
            (holder.cardView?.layoutParams as LinearLayout.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.dimen2)
        }
    }

    override fun getItemCount() = mList.size

    inner class TripsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mAvatarImageView = itemView.findViewById(R.id.avatar_imageview) as? ImageView
        val mTripFromTextView = itemView.findViewById(R.id.from_textView) as? TextView
        val mTripToTextView = itemView.findViewById(R.id.to_textView) as? TextView
        val mStatusTextView = itemView.findViewById(R.id.status_textView) as? TextView
        val mDateButton = itemView.findViewById(R.id.date_button) as? Button
        val mNameTextView = itemView.findViewById(R.id.client_name_textView) as? TextView
        val cardView = itemView.findViewById(R.id.cardView) as? CardView
    }

}