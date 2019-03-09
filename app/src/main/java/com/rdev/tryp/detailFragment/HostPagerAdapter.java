package com.rdev.tryp.detailFragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class HostPagerAdapter extends FragmentStatePagerAdapter {

    public HostPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        DetailFragment detailFragment = new DetailFragment();
        return new DetailFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
