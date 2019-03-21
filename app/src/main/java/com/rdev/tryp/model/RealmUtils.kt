package com.rdev.tryp.model

import android.content.Context
import android.util.Log
import com.rdev.tryp.model.login_response.Users
import com.rdev.tryp.payment.model.Card
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 * Copyright (c) 2019 Andrey Berezhnoi. All rights reserved.
 */

class RealmUtils(private val context: Context, private val callback: RealmCallback?){
    private val TAG = "realm"
    private val realm: Realm

    init {
        Realm.init(context)

        val config = RealmConfiguration.Builder()
                .schemaVersion(0)
                .migration(RealmMigration())
                .build()

        realm = Realm.getInstance(config)
    }

    fun getRealm() : Realm {
        return realm
    }

    fun pushUser(temp: Users) {
        realm.executeTransactionAsync({ bgRealm ->

            bgRealm.copyToRealmOrUpdate(temp)

        }, {
            Log.e(TAG, "User was updated")
            callback?.dataUpdated()
        }, { error ->
            error.printStackTrace()
            callback?.error()
        })
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

    fun deleteCard(id: String?){
        realm.executeTransactionAsync({ bgRealm ->
            val temps = bgRealm.where(Card::class.java).equalTo("id", id).findAll()
            temps.deleteAllFromRealm()
        }, {
            Log.e(TAG, "Card was deleted")
            callback?.dataUpdated()
        }, { error ->
            error.printStackTrace()
            callback?.error()
        })
    }

    fun clear(){
        realm.executeTransactionAsync({ bgRealm ->
            bgRealm.deleteAll()
        }, {
            Log.e(TAG, "Realm was deleted")
            callback?.dataUpdated()
        }, { error ->
            error.printStackTrace()
            callback?.error()
        })
    }
}