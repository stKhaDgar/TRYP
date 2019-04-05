package com.rdev.tryp.blocks.screens.help

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.rdev.tryp.R
import androidx.recyclerview.widget.RecyclerView
import io.github.inflationx.calligraphy3.CalligraphyTypefaceSpan
import io.github.inflationx.calligraphy3.TypefaceUtils

/**
 * Created by Alexey Matrosov on 04.03.2019.
 */


class RecentTripsAdapter(private val context: Context?, private val items: List<RecentTripItem>) : RecyclerView.Adapter<RecentTripsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recent_trip, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.userIcon?.setImageResource(if (item.userIcon == 0) R.drawable.invite_default_icon else item.userIcon)
        holder.isActive?.visibility = if (item.isActive) View.VISIBLE else View.INVISIBLE
        setDate(holder.time, item.date)
        holder.message?.text = item.message
        holder.amount?.text = item.amount
        holder.distance?.text = item.distance
    }

    override fun getItemCount() = items.size

    private fun setDate(dateView: TextView?, date: String) {
        val index = date.indexOf(", ")
        if (index == -1) {
            dateView?.text = date
        }

        val boldPart = date.substring(0, index)
        val coloredPart1Index = date.indexOf(boldPart)

        val spannable = SpannableString(date)
        if (coloredPart1Index != -1 && context != null) {
            val typeface = TypefaceUtils.load(context.assets, "fonts/OpenSans-Bold.ttf")

            spannable.setSpan(CalligraphyTypefaceSpan(typeface),
                    coloredPart1Index,
                    coloredPart1Index + boldPart.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        dateView?.setText(spannable, TextView.BufferType.SPANNABLE)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userIcon = itemView.findViewById(R.id.recent_user_icon) as? ImageView
        var isActive = itemView.findViewById(R.id.recent_active) as? View
        var time = itemView.findViewById(R.id.recent_date) as? TextView
        var message = itemView.findViewById(R.id.recent_message) as? TextView
        var amount = itemView.findViewById(R.id.recent_amount) as? TextView
        var distance = itemView.findViewById(R.id.recent_distance) as? TextView
    }

}