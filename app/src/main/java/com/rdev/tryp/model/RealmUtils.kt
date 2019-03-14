package com.rdev.tryp.model

import android.util.Log
import com.rdev.tryp.payment.model.Card
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */

class RealmUtils(private val callback: RealmCallback?){
    private val TAG = "realm"
    private val realm: Realm

    init {
        val config = RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(RealmMigration())
                .build()

        realm = Realm.getInstance(config)
    }

    fun getRealm() : Realm {
        return realm
    }

    fun pushCard(card: Card){
        realm.executeTransactionAsync({ bgRealm ->

            bgRealm.copyToRealmOrUpdate(card)

        }, {
            Log.e(TAG, "card was updated")
            callback?.dataUpdated()
        }, { error ->
            error.printStackTrace()
            callback?.error()
        })
    }

    fun getCards() : ArrayList<Card>{
        val array = ArrayList<Card>()

        realm.where(Card::class.java).findAll()?.let { list ->
            for(item in list){
                array.add(realm.copyFromRealm(item))
            }
        }

        return array
    }
}