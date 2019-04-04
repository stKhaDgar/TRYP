package com.rdev.tryp.payment.utils

/**
 * Created by Andrey Berezhnoi on 30.03.2019.
 */


object PaymentUtils{
    fun priceToPresentableFormat(price: Float) : String {
        return if(price % 1 == 0F) "$${price.toInt()}" else "$${String.format("%.2f", price)}"
    }
}