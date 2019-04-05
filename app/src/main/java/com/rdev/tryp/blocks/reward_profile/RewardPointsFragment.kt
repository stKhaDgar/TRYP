package com.rdev.tryp.blocks.reward_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R

import androidx.fragment.app.Fragment

class RewardPointsFragment : Fragment(), View.OnClickListener {
    private var root: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_reward, container, false)

        val fab = root?.findViewById<ImageView>(R.id.back_btn)
        fab?.setOnClickListener(this)
        root?.findViewById<View>(R.id.btnWithdraw)?.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_btn -> (activity as ContentActivity).startFragment(ContentActivity.TYPE_REWARDS)
            R.id.btnWithdraw -> (activity as ContentActivity).startFragment(ContentActivity.TYPE_REWARDS_WITHDRAW)
        }
    }

}