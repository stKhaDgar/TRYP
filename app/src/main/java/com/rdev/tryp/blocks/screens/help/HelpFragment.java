package com.rdev.tryp.blocks.screens.help;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rdev.tryp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alexey Matrosov on 04.03.2019.
 */
public class HelpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        View gradient = view.findViewById(R.id.gradient);
        gradient.setBackground(generateGradient(R.array.help_gradient));

        RecyclerView recyclerView = view.findViewById(R.id.help_trips_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecentTripsAdapter(getContext(), getFakeData()));

        ImageView backBtn = view.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private Drawable generateGradient(@ArrayRes int gradient) {
        int[] colors = getResources().getIntArray(gradient);
        GradientDrawable colorDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        colorDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        colorDrawable.setShape(GradientDrawable.RECTANGLE);

        return colorDrawable;
    }

    private List<RecentTripItem> getFakeData() {
        List<RecentTripItem> items = new ArrayList<>();

        items.add(new RecentTripItem(R.drawable.test_friend_icon, true, "Aug, 11:45 PM", "Cancelled Early", "$2.25", "1.3mi - 7m"));
        items.add(new RecentTripItem(R.drawable.test_friend_icon, true, "Aug, 11:45 PM", "Cancelled Early", "$2.25", "1.3mi - 7m"));
        items.add(new RecentTripItem(R.drawable.test_friend_icon, false, "Aug, 11:45 PM", "Cancelled Early", "$2.25", "1.3mi - 7m"));

        return items;
    }
}
