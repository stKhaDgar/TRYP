package com.rdev.tryp.blocks.trip_history.past_trip

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.firebaseDatabase.model.RecentRide
import com.rdev.tryp.utils.ViewUtils
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Andrey Berezhnoi on 15.04.2019.
 */


class PastTripAdapter(private val context: Context, private val mList: ArrayList<RecentRide>) : RecyclerView.Adapter<PastTripAdapter.TripsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsHolder {
        val inflater = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        return TripsHolder(inflater)
    }

    override fun onBindViewHolder(holder: TripsHolder, position: Int) {
        val model = mList[position]

        Picasso.get().load(model.driver?.image).into(holder.mAvatarImageView)
        holder.mTripFromTextView?.text = model.fromAddress
        holder.mTripToTextView?.text = model.destinationAddress
        model.dateCreatedAt?.let { date -> holder.mDateButton?.text = SimpleDateFormat("d MMM 'at' h:mm a", Locale.ENGLISH).format(date) }
        holder.mNameTextView?.text = model.driver?.firstName
        model.distance?.let { distance -> holder.tvDistance?.text = ViewUtils.distanceToPresentableFormat(distance) }

        when(model.status){
            ConstantsFirebase.STATUS_RIDE_CONFIRMED -> {
                holder.mStatusTextView?.setTextColor(ContextCompat.getColor(context, R.color.confirmed))
                holder.mStatusTextView?.text = "Confirmed"
                (holder.mStatusTextView?.layoutParams as? ConstraintLayout.LayoutParams)?.bottomMargin = context.resources.getDimensionPixelSize(R.dimen.dimen8)
                holder.tvPrice?.visibility = View.VISIBLE
                model.fare?.let { fare -> holder.tvPrice?.text = ViewUtils.priceToPresentableFormat(fare * 0.8F) }
            }
            ConstantsFirebase.STATUS_RIDE_CANCELLED -> {
                holder.mStatusTextView?.setTextColor(ContextCompat.getColor(context, R.color.cancelled))
                holder.mStatusTextView?.text = "Cancelled"
                (holder.mStatusTextView?.layoutParams as? ConstraintLayout.LayoutParams)?.bottomMargin = 0
                holder.tvPrice?.visibility = View.GONE
            }
        }

        when (position) {
            0 -> {
                (holder.cardView?.layoutParams as ConstraintLayout.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.dimen24)
                (holder.mainLayout?.layoutParams as ConstraintLayout.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.dimen24)
            }
            mList.size-1 -> {
                (holder.cardView?.layoutParams as ConstraintLayout.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.dimen2)
                (holder.cardView.layoutParams as ConstraintLayout.LayoutParams).bottomMargin = context.resources.getDimensionPixelSize(R.dimen.dimen24)
                (holder.mainLayout?.layoutParams as ConstraintLayout.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.dimen2)
                (holder.mainLayout.layoutParams as ConstraintLayout.LayoutParams).bottomMargin = context.resources.getDimensionPixelSize(R.dimen.dimen24)
            }
        }
    }

    override fun getItemCount() = mList.size

    inner class TripsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mAvatarImageView = itemView.findViewById(R.id.avatar_imageview) as? ImageView
        val mTripFromTextView = itemView.findViewById(R.id.from_textView) as? TextView
        val mTripToTextView = itemView.findViewById(R.id.to_textView) as? TextView
        val mStatusTextView = itemView.findViewById(R.id.status_textView) as? TextView
        val tvPrice = itemView.findViewById(R.id.tvPrice) as? TextView
        val tvDistance = itemView.findViewById(R.id.tvDistance) as? TextView
        val mDateButton = itemView.findViewById(R.id.date_button) as? Button
        val mNameTextView = itemView.findViewById(R.id.client_name_textView) as? TextView
        val cardView = itemView.findViewById(R.id.cardView) as? CardView
        val mainLayout = itemView.findViewById(R.id.main_layout) as? ConstraintLayout
    }

}