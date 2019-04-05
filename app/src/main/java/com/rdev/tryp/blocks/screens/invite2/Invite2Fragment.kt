package com.rdev.tryp.blocks.screens.invite2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rdev.tryp.R

import java.util.ArrayList
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */


class Invite2Fragment : Fragment() {

    private var hereItems: MutableList<InviteItem>? = null
    private var inviteItems: MutableList<InviteItem>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_invite2, container, false)

        fillFakeData()

        val hereRecyclerView = view.findViewById<RecyclerView>(R.id.invite_2_here_container)
        hereItems?.let { items -> hereRecyclerView.adapter = InviteFriendsAdapter(items) }
        hereRecyclerView.layoutManager = LinearLayoutManager(context)

        val inviteRecyclerView = view.findViewById<RecyclerView>(R.id.invite_2_invite_container)
        inviteItems?.let { items -> inviteRecyclerView.adapter = InviteFriendsAdapter(items) }
        inviteRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    private fun fillFakeData() {
        hereItems = ArrayList()
        hereItems?.add(InviteItem(R.drawable.test_friend_icon, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.Invited))
        hereItems?.add(InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.Followed))
        hereItems?.add(InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.Invited))

        inviteItems = ArrayList()
        inviteItems?.add(InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.NotInvited))
        inviteItems?.add(InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.NotInvited))
        inviteItems?.add(InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.NotInvited))
    }

}