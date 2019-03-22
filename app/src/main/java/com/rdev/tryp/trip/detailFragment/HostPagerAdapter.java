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

    public HostPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setDrivers(List<?> drivers){
        this.drivers = drivers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        DetailFragment detailFragment = new DetailFragment((Driver) drivers.get(position));
        return detailFragment;
    }

    @Override
    public int getCount() {
       return drivers.size();
    }
}