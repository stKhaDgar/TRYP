package com.rdev.tryp.utils


import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import com.google.gson.Gson
import com.rdev.tryp.model.TripPlace

object PreferenceManager {

    private val TAG = PreferenceManager::class.java.simpleName

    private val PREF_NAME = "testAppPreference"

    private var sPref: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null

    fun init(context: Context) {
        //custom shared preferences
        //        sPref = MainApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        //default shared preferences
        sPref = android.preference.PreferenceManager.getDefaultSharedPreferences(context)
        edit = sPref?.edit()
    }

    fun getPrefs(cxt: Context): SharedPreferences? {
        if (edit == null) {
            init(cxt)
        }
        return sPref
    }

    fun setStringSet(key: String, value: Set<String>) {
        Log.i(TAG, "setString $key=$value")
        edit?.putStringSet(key, value)
        edit?.commit()
    }

    fun setString(key: String, value: String) {
        Log.i(TAG, "setString $key=$value")
        edit?.putString(key, value)
        edit?.commit()
    }

    fun setLong(key: String, value: Long) {
        Log.i(TAG, "setLong $key=$value")
        edit?.putLong(key, value)
        edit?.commit()
    }

    fun setBoolean(key: String, value: Boolean) {
        Log.i(TAG, "setBoolean $key=$value")
        edit?.putBoolean(key, value)
        edit?.commit()
    }

    fun setInt(key: String, value: Int) {
        Log.i(TAG, "setInt $key=$value")
        edit?.putInt(key, value)
        edit?.commit()
    }

    fun setPrefFloat(key: String, value: Float) {
        Log.i(TAG, "setPrefFloat $key=$value")
        edit?.putFloat(key, value)
        edit?.commit()
    }

    fun setTripPlace(key: String, `object`: TripPlace?) {
        val gson = Gson()
        val json = gson.toJson(`object`)
        edit?.putString(key, json)
        edit?.commit()
    }

    fun getTripPlace(key: String): TripPlace? {
        val gson = Gson()
        val json = sPref?.getString(key, "")
        return if (json?.isEmpty() == true) {
            null
        } else gson.fromJson(json, TripPlace::class.java)
    }

    fun getStringSet(key: String): Set<String>? {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            sPref?.getStringSet(key, null)
        } else null
    }

    fun getString(key: String): String? {
        return sPref?.getString(key, null)
    }

    fun getString(key: String, def: String): String? {
        return sPref?.getString(key, def)
    }

    fun getBoolean(key: String): Boolean? {
        return sPref?.getBoolean(key, false)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean? {
        return sPref?.getBoolean(key, defValue)
    }

    fun getLong(key: String): Long? {
        return sPref?.getLong(key, -1)
    }

    fun getInt(key: String): Int? {
        return sPref?.getInt(key, -1)
    }

    fun getInt(key: String, def: Int): Int? {
        return sPref?.getInt(key, def)
    }

    fun getFloat(key: String): Float? {
        return sPref?.getFloat(key, -1f)
    }

}