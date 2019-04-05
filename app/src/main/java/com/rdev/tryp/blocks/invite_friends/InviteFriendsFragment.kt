package com.rdev.tryp.blocks.invite_friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R

import androidx.fragment.app.Fragment


class InviteFriendsFragment : Fragment(), View.OnClickListener {

    private var root: View? = null
    private var backBtn: ImageView? = null
    private var shareBtn: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_invite_friends, container, false)

        initView()

        return root
    }

    private fun initView() {
        backBtn = root?.findViewById(R.id.back_btn)
        shareBtn = root?.findViewById(R.id.share_button)

        backBtn?.setOnClickListener(this)
        shareBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_btn -> (activity as? ContentActivity)?.popBackStack()
            R.id.share_button -> Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
        }
    }

}