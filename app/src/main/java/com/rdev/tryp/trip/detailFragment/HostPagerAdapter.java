package com.rdev.tryp.trip.detailFragment;

import com.rdev.tryp.firebaseDatabase.model.Driver;
import com.rdev.tryp.model.DriversItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class HostPagerAdapter extends FragmentStatePagerAdapter {

    private List<?> drivers;

    HostPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(drivers.isEmpty() || drivers.size() == 0){
            return null;
        }
        return new DetailFragment((Driver) drivers.get(position));
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