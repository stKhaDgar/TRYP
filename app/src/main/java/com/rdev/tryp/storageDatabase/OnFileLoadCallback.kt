package com.rdev.tryp.storageDatabase

import java.lang.Exception

/**
 * Created by Andrey Berezhnoi on 29.03.2019.
 */


interface OnFileLoadCallback {
    fun dataUpdated(url: String)
    fun error(e: Exception)
}