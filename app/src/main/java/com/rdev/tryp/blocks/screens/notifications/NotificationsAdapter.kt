package com.rdev.tryp.blocks.screens.notifications

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
 * Created by Alexey Matrosov on 03.03.2019.
 */


class NotificationsAdapter(private val context: Context?, private val items: List<NotificationItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_PROMO = 1
    }

    override fun getItemViewType(position: Int) = when (items[position].type) {
        NotificationItem.Type.Promo -> TYPE_PROMO
        NotificationItem.Type.Normal -> TYPE_TEXT
        else -> throw IllegalStateException("Unknown item type")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_TEXT -> {
            val view = LayoutInflater.from(context).inflate(R.layout.item_notificatio_text, parent, false)
            TextViewHolder(view)
        }

        TYPE_PROMO -> {
            val view = LayoutInflater.from(context).inflate(R.layout.item_notification_promo, parent, false)
            PromoViewHolder(view)
        }
        else -> throw IllegalStateException("Unknown item type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_TEXT -> {
                val textHolder = holder as TextViewHolder
                val item = items[position]

                textHolder.name.text = item.name
                textHolder.message.text = item.message
                textHolder.userIcon.setImageResource(if (item.userIcon == 0) R.drawable.invite_default_icon else item.userIcon)
                textHolder.time.text = item.time
            }

            TYPE_PROMO -> {
                val promoHolder = holder as PromoViewHolder
                val item = items[position]

                promoHolder.promoImage.setImageResource(item.phomoImage)
                promoHolder.title.text = item.title
                promoHolder.time.text = item.time
                item.description?.let { description ->
                    item.code?.let { code ->
                        changeCodePart(promoHolder.description, description, code)
                    }
                }
            }
            else -> throw IllegalStateException("Unknown item type")
        }
    }

    override fun getItemCount() = items.size

    private fun changeCodePart(description: TextView, text: String, code: String) {
        val coloredPart1Index = text.indexOf(code)

        val spannable = SpannableString(text)
        if (coloredPart1Index != -1 && context != null) {
            val typeface = TypefaceUtils.load(context.assets, "fonts/OpenSans-Bold.ttf")

            spannable.setSpan(CalligraphyTypefaceSpan(typeface),
                    coloredPart1Index,
                    coloredPart1Index + code.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        description.setText(spannable, TextView.BufferType.SPANNABLE)
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userIcon: ImageView = itemView.findViewById(R.id.notification_icon_container)
        var name: TextView = itemView.findViewById(R.id.item_notification_title)
        var message: TextView = itemView.findViewById(R.id.item_notification_message)
        var time: TextView = itemView.findViewById(R.id.notification_time)
    }

    inner class PromoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var promoImage: ImageView = itemView.findViewById(R.id.notification_promo)
        var title: TextView = itemView.findViewById(R.id.notification_promo_title)
        var description: TextView = itemView.findViewById(R.id.notification_promo_description)
        var time: TextView = itemView.findViewById(R.id.notification_time)
    }

}