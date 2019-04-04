package com.rdev.tryp.payment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.braintreepayments.cardform.utils.CardType
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.payment.model.Card

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 */

class PaymentCardsAdapter(private val itemList: ArrayList<Card>, private val context: Activity): RecyclerView.Adapter<PaymentCardsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        item.type?.let { type ->
            holder.icon?.setImageResource(CardType.valueOf(type).frontResource)
        }

        item.number?.let { text ->
            val number = "****  ****  ****  ${text.substring(12, text.length)}"
            holder.number?.text = number
        }

        holder.mainLayout?.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(ContentActivity.IS_EDIT_CARD, item)
            if(context is ContentActivity){
                context.b = bundle
                context.startFragment(ContentActivity.TYPE_PAYMENT_NEW_ENTRY)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_payment_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mainLayout = itemView.findViewById(R.id.main_layout) as? LinearLayout
        val icon = itemView.findViewById(R.id.card_type) as? ImageView
        val number = itemView.findViewById(R.id.card_number) as? TextView
    }
}