package com.rdev.tryp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentManager;

public class Utils {
    public static String getCountryCode(String data) {
        return data.substring(data.indexOf(",") + 1, data.lastIndexOf(","));
    }

    public static String getDialingCode(String data) {
        return data.substring(0, data.indexOf(","));

    }

    public static String getCountryName(String data) {
        return data.substring(data.lastIndexOf(",") + 1, data.length());


    }

    public static boolean isFragmentInBackstack(final FragmentManager fragmentManager,
                                                final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

    public static void showKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
