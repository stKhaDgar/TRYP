package com.rdev.tryp.model

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 */


interface RealmCallback {
    fun dataUpdated()
    fun error()
}