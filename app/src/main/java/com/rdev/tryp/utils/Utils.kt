package com.rdev.tryp.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager

import androidx.fragment.app.FragmentManager

object Utils {

    const val HOME_ADDRESS = "home"
    const val WORK_ADDRESS = "work"
    const val KEY_RECENT_FROM_1 = "KEY_RECENT_FROM_1"
    const val KEY_RECENT_TO_1 = "KEY_RECENT_TO_1"
    const val KEY_RECENT_FROM_2 = "KEY_RECENT_FROM_2"
    const val KEY_RECENT_TO_2 = "KEY_RECENT_TO_2"
    const val KEY_HOME = "KEY_HOME"
    const val KEY_WORK = "KEY_WORK"

    fun getCountryCode(data: String) = data.substring(data.indexOf(",") + 1, data.lastIndexOf(","))

    fun getDialingCode(data: String) = data.substring(0, data.indexOf(","))

    fun getCountryName(data: String) = data.substring(data.lastIndexOf(",") + 1, data.length)

    fun isFragmentInBackStack(fragmentManager: FragmentManager,
                              fragmentTagName: String): Boolean {
        for (entry in 0 until fragmentManager.backStackEntryCount) {
            if (fragmentTagName == fragmentManager.getBackStackEntryAt(entry).name) {
                return true
            }
        }
        return false
    }

    fun showKeyboard(context: Context?) {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard(context: Context?) {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}