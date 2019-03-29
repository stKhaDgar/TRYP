package com.rdev.tryp.storageDatabase.utils

import com.google.firebase.storage.FirebaseStorage
import com.rdev.tryp.firebaseDatabase.ConstantsFirebase
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.storageDatabase.OnFileLoadCallback

/**
 * Created by Andrey Berezhnoi on 29.03.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


class StorageUtils{
    private val storageRef = FirebaseStorage.getInstance().reference

    fun pushPhoto(bytes: ByteArray, callback: OnFileLoadCallback){
        val clients = storageRef.child(ConstantsFirebase.CLIENTS)
        val imageRef = clients.child("${RealmUtils(null, null).getCurrentUser()?.userId}.jpg")

        imageRef.putBytes(bytes).addOnFailureListener {
            callback.error(it)
        }.addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                callback.dataUpdated(uri.toString())
            }.addOnFailureListener {
                callback.error(it)
            }
        }
    }
}