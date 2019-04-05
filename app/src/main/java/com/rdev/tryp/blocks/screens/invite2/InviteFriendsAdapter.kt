package com.rdev.tryp.blocks.screens.invite2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.rdev.tryp.R
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */


class InviteFriendsAdapter(private val items: List<InviteItem>) : RecyclerView.Adapter<InviteFriendsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_invite_friends, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.icon?.setImageResource(if (item.icon == 0) R.drawable.invite_default_icon else item.icon)

        holder.name?.text = item.name
        holder.phone?.text = item.number

        when (item.status) {
            InviteItem.Status.NotInvited -> {
                holder.notInvitedContainer?.visibility = View.VISIBLE
                holder.invitedContainer?.visibility = View.GONE
                holder.followedContainer?.visibility = View.GONE
            }
            InviteItem.Status.Invited -> {
                holder.notInvitedContainer?.visibility = View.GONE
                holder.invitedContainer?.visibility = View.VISIBLE
                holder.followedContainer?.visibility = View.GONE
            }
            InviteItem.Status.Followed -> {
                holder.notInvitedContainer?.visibility = View.GONE
                holder.invitedContainer?.visibility = View.GONE
                holder.followedContainer?.visibility = View.VISIBLE
            }
            else -> throw IllegalStateException("Unknown status")
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon = itemView.findViewById(R.id.item_invite_icon_container) as? ImageView
        var name = itemView.findViewById(R.id.item_invite_title) as? TextView
        var phone = itemView.findViewById(R.id.item_invite_phone) as? TextView
        var notInvitedContainer = itemView.findViewById(R.id.not_invited_container) as? View
        var invitedContainer = itemView.findViewById(R.id.invited_container) as? View
        var followedContainer = itemView.findViewById(R.id.followed_container) as? View
    }

}