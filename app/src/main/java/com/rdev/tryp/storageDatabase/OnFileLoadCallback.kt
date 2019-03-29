package com.rdev.tryp.storageDatabase

import java.lang.Exception

/**
 * Created by Andrey Berezhnoi on 29.03.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


interface OnFileLoadCallback {
    fun dataUpdated(url: String)
    fun error(e: Exception)
}