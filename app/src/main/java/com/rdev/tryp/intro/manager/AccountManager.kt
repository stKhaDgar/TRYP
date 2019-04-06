package com.rdev.tryp.intro.manager

import android.content.Context
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.utils.PreferenceManager

/**
 * Created by Alexey Matrosov on 06.03.2019.
 */


class AccountManager {

    var userId: Int = -1

    var isUserSignIn = userId != -1

    fun signIn(userId: Int) {
        this.userId = userId
        PreferenceManager.setInt(USER_ID_KEY, userId)
    }

    fun signOut(context: Context) {
        userId = -1
        PreferenceManager.setInt(USER_ID_KEY, userId)
        val realm = RealmUtils(context, null)
        realm.clear()
    }

    companion object {
        private var instance: AccountManager? = null
        private const val USER_ID_KEY = "USER_ID_KEY"

        fun getInstance(): AccountManager? {
            if (instance == null)
                instance = AccountManager()
            PreferenceManager.getInt(USER_ID_KEY)?.let { userId ->
                instance?.userId = userId
            }
            instance?.isUserSignIn = instance?.userId != -1

            return instance
        }
    }

}