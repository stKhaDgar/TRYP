package com.rdev.tryp.ui_only.screens.invite2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdev.tryp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */
public class Invite2Fragment extends Fragment {
    private List<InviteItem> hereItems;
    private List<InviteItem> inviteItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invite2, container, false);

        fillFakeData();

        RecyclerView hereRecyclerView = view.findViewById(R.id.invite_2_here_container);
        hereRecyclerView.setAdapter(new InviteFriendsAdapter(getContext(), hereItems));
        hereRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView inviteRecyclerView = view.findViewById(R.id.invite_2_invite_container);
        inviteRecyclerView.setAdapter(new InviteFriendsAdapter(getContext(), inviteItems));
        inviteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    void fillFakeData() {
        hereItems = new ArrayList<>();
        hereItems.add(new InviteItem(R.drawable.test_friend_icon, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.Invited));
        hereItems.add(new InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.Followed));
        hereItems.add(new InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.Invited));

        inviteItems = new ArrayList<>();
        inviteItems.add(new InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.NotInvited));
        inviteItems.add(new InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.NotInvited));
        inviteItems.add(new InviteItem(0, "Alex Bingo", "+1-200-4000-2525", InviteItem.Status.NotInvited));
    }
}
