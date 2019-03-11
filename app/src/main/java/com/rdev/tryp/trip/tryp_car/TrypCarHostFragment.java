package com.rdev.tryp.trip.tryp_car;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class TrypCarHostFragment extends Fragment {
    ViewPager viewPager;
    ImageButton back_btn;
    TrypCarAdapter adapter;
    private List<?> drivers = new ArrayList<>();
    private int currentPos;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.car_host_fragment, container, false);
        viewPager = v.findViewById(R.id.detail_viewpager);
        TrypCarAdapter adapter = new TrypCarAdapter(getChildFragmentManager());
        adapter.setDrivers(drivers);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPos);
        viewPager.setPageMargin(dpToPx(8));
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ContentActivity) getActivity()).popBackStack();
            }
        });
        return v;
    }

    public void setDrivers(List<?> drivers, int currentPos){
        this.drivers = drivers;
        this.currentPos = currentPos;
        if(adapter != null) {
            adapter.setDrivers(drivers);
        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
