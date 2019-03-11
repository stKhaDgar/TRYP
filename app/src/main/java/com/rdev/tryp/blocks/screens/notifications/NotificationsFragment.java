package com.rdev.tryp.blocks.screens.notifications;

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
 * Created by Alexey Matrosov on 03.03.2019.
 */
public class NotificationsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        List<NotificationItem> items = getFakeData();

        RecyclerView recyclerView = view.findViewById(R.id.notifications_container);
        recyclerView.setAdapter(new NotificationsAdapter(getContext(), items));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private List<NotificationItem> getFakeData() {
        List<NotificationItem> items = new ArrayList<>();

        items.add(NotificationItem.createPromoNotification("2 days ago", R.drawable.test_promo, "Enjoy our lowest Fares", "Use code TRAVEL 250 to get flat Rs. 250 off on your first Outstation Booking.", "TRAVEL 250"));
        items.add(NotificationItem.createNormalNotification("10min ago", R.drawable.test_friend_icon, "Bob Swanson", "I am waiting"));
        items.add(NotificationItem.createNormalNotification("20min ago", R.drawable.test_friend_icon, "Bob Swanson", "I am waiting"));
        items.add(NotificationItem.createNormalNotification("30min ago", 0, "Bob Swanson", "Hi, I'm here :)"));

        return items;
    }
}