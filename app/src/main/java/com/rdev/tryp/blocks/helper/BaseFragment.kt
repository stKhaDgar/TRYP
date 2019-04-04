package com.rdev.tryp.blocks.helper

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.rdev.tryp.ContentActivity

/**
 * Created by Andrey Berezhnoi on 04.04.2019.
 */


abstract class BaseFragment: Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                (activity as ContentActivity).openMap()
                true
            } else {
                false
            }
        }
    }

}