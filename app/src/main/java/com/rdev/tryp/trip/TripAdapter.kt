package com.rdev.tryp.trip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.rdev.tryp.R
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.firebaseDatabase.model.Driver
import com.rdev.tryp.payment.utils.PaymentUtils

import java.util.ArrayList
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class TripAdapter(var drivers: ArrayList<Driver>, var type: Int, private val listener: OnItemClickListener, private val context: Context, private val fare: Float?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_CAR = 1
        const val TYPE_DRIVER = 2
    }

    interface OnItemClickListener {
        fun onItemClick(item: Any)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (type == TYPE_CAR) {
        CarHolder(LayoutInflater.from(parent.context).inflate(R.layout.trip_car_item, parent, false))
    } else {
        DriverHolder(LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false))
    }

    inner class CarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivCar: ImageView = itemView.findViewById(R.id.avatar_iv)
        var category: TextView = itemView.findViewById(R.id.category_tv)
        var tvTypeCar: TextView = itemView.findViewById(R.id.car_type)
        var tvNumOfPassengers: TextView = itemView.findViewById(R.id.num_of_passangers)
        var tvNumOfBaggage: TextView = itemView.findViewById(R.id.num_of_baggage)
        var tvFare: TextView = itemView.findViewById(R.id.fare)
        var priceCardView: CardView = itemView.findViewById(R.id.price_card_view)
        var mainLayout: CardView = itemView.findViewById(R.id.main_layout)

        init {
            itemView.setOnClickListener { listener.onItemClick(drivers[adapterPosition]) }
        }
    }

    internal inner class DriverHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivAvatar: ImageView = itemView.findViewById(R.id.avatar_iv)
        var tvDriver: TextView = itemView.findViewById(R.id.driver_name)
        var tvCategory: TextView = itemView.findViewById(R.id.driver_category)
        var tvFare: TextView = itemView.findViewById(R.id.fare)
        var tvNumOfPassengers: TextView = itemView.findViewById(R.id.num_of_passangers)
        var tvNumOfBaggage: TextView = itemView.findViewById(R.id.num_of_baggage)
        var priceCardView: CardView = itemView.findViewById(R.id.price_card_view)
        var mainLayout: CardView = itemView.findViewById(R.id.main_layout)

        init {
            itemView.setOnClickListener { listener.onItemClick(drivers[adapterPosition]) }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CarHolder) {
            val item = drivers[position]

            item.vehicle?.image?.let { field ->
                Glide.with(holder.itemView).load(field).into(holder.ivCar)
            }

            //            carHolder.ivCar.setImageDrawable(ContextCompat.getDrawable(context,
            //                    TrypCarFragment.getImageByType(item.getCategory())));
            //Log.i("adapter", "" + item.getCategory());
            holder.category.text = item.category
            holder.tvTypeCar.text = item.type
            holder.tvNumOfPassengers.text = "${item.maxPassenger}"
            holder.tvNumOfBaggage.text = "${item.maxLuggage}"
            fare?.let { num -> holder.tvFare.text = PaymentUtils.priceToPresentableFormat(num * ConstantsFirebase.TRYP_CAR_FARE) }

            val lp = holder.mainLayout.layoutParams as RecyclerView.LayoutParams
            if (position == 0) {
                lp.leftMargin = context.resources.getDimensionPixelOffset(R.dimen.dimen16)
            } else if (position == drivers.size - 1) {
                lp.rightMargin = context.resources.getDimensionPixelOffset(R.dimen.dimen16)
            }

            holder.mainLayout.requestLayout()

        } else {
            val driverHolder = holder as DriverHolder
            val ivAvatar = driverHolder.ivAvatar
            val tvDriver = driverHolder.tvDriver
            val tvCategory = driverHolder.tvCategory
            val item = drivers[position]
            Glide.with(holder.itemView).load(item.image).into(ivAvatar)
            tvDriver.text = "${item.firstName} ${item.lastName}"
            tvCategory.text = item.category
            fare?.let { num -> driverHolder.tvFare.text = PaymentUtils.priceToPresentableFormat(num * ConstantsFirebase.TRYP_CAR_FARE) }
            driverHolder.tvNumOfPassengers.text = "${item.maxPassenger}"
            driverHolder.tvNumOfBaggage.text = "${item.maxLuggage}"

            val lp = driverHolder.mainLayout.layoutParams as RecyclerView.LayoutParams
            if (position == 0) {
                lp.leftMargin = context.resources.getDimensionPixelOffset(R.dimen.dimen16)
            } else if (position == drivers.size - 1) {
                lp.rightMargin = context.resources.getDimensionPixelOffset(R.dimen.dimen16)
            }
            driverHolder.mainLayout.requestLayout()
        }
    }

    override fun getItemCount() = drivers.size

}