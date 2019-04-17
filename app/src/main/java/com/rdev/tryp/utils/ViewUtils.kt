package com.rdev.tryp.utils

/**
 * Created by Andrey Berezhnoi on 30.03.2019.
 */


object ViewUtils{

    fun priceToPresentableFormat(price: Float): String {
        return if(price % 1 == 0F) "$${price.toInt()}" else "$${String.format("%.2f", price)}"
    }

    fun distanceToPresentableFormat(distance: Double): String {
        return if(distance % 1 == 0.0) "${distance.toInt()} km" else "${String.format("%.2f", distance)} km"
    }

}