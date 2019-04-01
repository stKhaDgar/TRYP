package com.rdev.tryp.blocks.screens.completedRide

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rdev.tryp.ContentActivity

import com.rdev.tryp.R
import kotlinx.android.synthetic.main.fragment_feedback_driver.view.*

class FeedbackDriverFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_feedback_driver, container, false)

        onClickListener(root)
        return root
    }

    private fun onClickListener(view: View){
        view.back_btn.setOnClickListener {
            (activity as ContentActivity).goHomeOneTransition()
        }

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                (activity as ContentActivity).goHomeOneTransition()
                true
            } else {
                false
            }
        }
    }

}