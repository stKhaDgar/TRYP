package com.rdev.tryp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.rdev.tryp.utils.Utils
import androidx.recyclerview.widget.RecyclerView

import com.rdev.tryp.utils.Utils.getCountryName
import com.rdev.tryp.utils.Utils.getDialingCode


class PickerAdapter(var data: Array<String>, var listener: SelectCountryListener) : RecyclerView.Adapter<PickerAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.phone_item, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val country = Utils.getCountryName(data[position]) + " (+" + Utils.getDialingCode(data[position]) + ")"
        holder.countryTv?.text = country
    }

    override fun getItemCount() = data.size

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countryTv = itemView.findViewById(R.id.country_tv) as? TextView

        init {
            itemView.setOnClickListener { listener.onSelect(data[adapterPosition]) }
        }
    }

}