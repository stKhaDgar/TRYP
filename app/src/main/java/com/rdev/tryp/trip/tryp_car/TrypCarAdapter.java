package com.rdev.tryp.trip.tryp_car;

import com.rdev.tryp.model.DriversItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class TrypCarAdapter extends FragmentStatePagerAdapter {

    private List<?> drivers;
    public TrypCarAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TrypCarFragment fragment;
        if(drivers.isEmpty() || drivers.size() == 0){
            return null;
        }
        return new TrypCarFragment((DriversItem) drivers.get(position));
    }

    public void setDrivers(List<?> drivers){
        this.drivers = drivers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return drivers.size();
    }
}
