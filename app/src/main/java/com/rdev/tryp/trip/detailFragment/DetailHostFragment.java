package com.rdev.tryp.trip.detailFragment;

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

public class DetailHostFragment extends Fragment {

    private ViewPager viewPager;
    private ImageButton back_btn;
    private List<?> drivers = new ArrayList<>();
    private int currentPos;
    private HostPagerAdapter adapter;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.detail_host_fragment, container, false);
        viewPager = v.findViewById(R.id.detail_viewpager);
        adapter = new HostPagerAdapter(getChildFragmentManager());
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

    public void setDrivers(List<?> drivers, int currentPos) {
        this.drivers = drivers;
        this.currentPos = currentPos;
        if (adapter != null) {
            adapter.setDrivers(drivers);
        }
    }


    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}