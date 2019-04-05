package com.rdev.tryp.blocks.screens.notifications

import androidx.annotation.DrawableRes

/**
 * Created by Alexey Matrosov on 03.03.2019.
 */


class NotificationItem {

    var type: Type? = null
        private set
    var time: String? = null
        private set // Replace to timestamp in real code

    // Normal fields
    @DrawableRes
    var userIcon: Int = 0
        private set
    var name: String? = null
        private set
    var message: String? = null
        private set

    // Promo fields
    @DrawableRes
    var phomoImage: Int = 0
        private set
    var title: String? = null
        private set
    var description: String? = null
        private set
    var code: String? = null
        private set

    enum class Type {
        Normal, Promo
    }

    companion object {

        fun createNormalNotification(time: String, @DrawableRes userIcon: Int, name: String, message: String): NotificationItem {
            val item = NotificationItem()
            item.type = Type.Normal
            item.time = time
            item.userIcon = userIcon
            item.name = name
            item.message = message

            return item
        }

        fun createPromoNotification(time: String, @DrawableRes promoIcon: Int, title: String, description: String, code: String): NotificationItem {
            val item = NotificationItem()
            item.type = Type.Promo
            item.time = time
            item.phomoImage = promoIcon
            item.title = title
            item.description = description
            item.code = code

            return item
        }
    }

}
