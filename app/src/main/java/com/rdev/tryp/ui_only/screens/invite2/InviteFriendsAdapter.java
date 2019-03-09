package com.rdev.tryp.ui_only.screens.invite2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdev.tryp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */
public class InviteFriendsAdapter extends RecyclerView.Adapter<InviteFriendsAdapter.ViewHolder> {

    private Context context;
    private List<InviteItem> items;

    public InviteFriendsAdapter(Context context, List<InviteItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invite_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InviteItem item = items.get(position);

        holder.icon.setImageResource(item.getIcon() == 0 ? R.drawable.invite_default_icon : item.getIcon());

        holder.name.setText(item.getName());
        holder.phone.setText(item.getNumber());

        switch (item.getStatus()) {
            case NotInvited: {
                holder.notInvitedContainer.setVisibility(View.VISIBLE);
                holder.invitedContainer.setVisibility(View.GONE);
                holder.followedContainer.setVisibility(View.GONE);
                break;
            }
            case Invited: {
                holder.notInvitedContainer.setVisibility(View.GONE);
                holder.invitedContainer.setVisibility(View.VISIBLE);
                holder.followedContainer.setVisibility(View.GONE);
                break;
            }
            case Followed: {
                holder.notInvitedContainer.setVisibility(View.GONE);
                holder.invitedContainer.setVisibility(View.GONE);
                holder.followedContainer.setVisibility(View.VISIBLE);
                break;
            }
            default: throw new IllegalStateException("Unknown status");
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView phone;

        View notInvitedContainer;
        View invitedContainer;
        View followedContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.item_invite_icon_container);
            name = itemView.findViewById(R.id.item_invite_title);
            phone = itemView.findViewById(R.id.item_invite_phone);

            notInvitedContainer = itemView.findViewById(R.id.not_invited_container);
            invitedContainer = itemView.findViewById(R.id.invited_container);
            followedContainer = itemView.findViewById(R.id.followed_container);
        }
    }
}
