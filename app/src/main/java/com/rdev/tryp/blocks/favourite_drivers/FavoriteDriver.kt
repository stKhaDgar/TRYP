package com.rdev.tryp.blocks.favourite_drivers

/**
 * Created by Andrey Berezhnoi on 02.04.2019.
 */


class FavoriteDriver(firstName: String?, lastName: String?) {

    var id: String? = null
    val title = "$firstName $lastName"
    var category: String? = null
    var isLike: Boolean = true
    var photo: String? = null

}