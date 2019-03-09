package com.rdev.tryp.tryp_car;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class TrypCarAdapter extends FragmentStatePagerAdapter {

    public TrypCarAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TrypCarFragment fragment;
        switch (position) {
            case 0:
                fragment = new TrypCarFragment(TrypCarFragment.TYPE_TRYP);
                return fragment;
            case 1:
                fragment = new TrypCarFragment(TrypCarFragment.TYPE_TRYP_ASSIST);
                return fragment;
            case 2:
                fragment = new TrypCarFragment(TrypCarFragment.TYPE_TRYP_EXTRA);
                return fragment;
            case 3:
                fragment = new TrypCarFragment(TrypCarFragment.TYPE_TRYP_PRIME);
                return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
