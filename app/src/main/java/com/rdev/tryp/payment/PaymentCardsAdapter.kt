package com.rdev.tryp.payment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.braintreepayments.cardform.utils.CardType
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.payment.model.Card

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */

class PaymentCardsAdapter(private val itemList: ArrayList<Card>, private val context: Activity): RecyclerView.Adapter<PaymentCardsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemList[position].type?.let { type ->
            holder.icon?.setImageResource(CardType.valueOf(type).frontResource)
        }
        itemList[position].number?.let { text ->
            val number = "**** **** **** ${text.substring(12, text.length)}"

            holder.number?.text = number
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(ContentActivity.IS_EDIT_CARD, itemList[position])
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
        val icon = itemView.findViewById(R.id.card_type) as? ImageView
        val number = itemView.findViewById(R.id.card_number) as? TextView
    }
}