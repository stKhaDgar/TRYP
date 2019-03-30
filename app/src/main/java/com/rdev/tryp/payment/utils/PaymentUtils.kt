package com.rdev.tryp.payment.utils

/**
 * Created by Andrey Berezhnoi on 30.03.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


object PaymentUtils{
    fun priceToPresentableFormat(price: Float) : String {
        return if(price % 1 == 0F) "$${price.toInt()}" else "$${String.format("%.2f", price)}"
    }
}