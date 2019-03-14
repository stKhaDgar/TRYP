package com.rdev.tryp.model

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


interface RealmCallback {
    fun dataUpdated()
    fun error()
}