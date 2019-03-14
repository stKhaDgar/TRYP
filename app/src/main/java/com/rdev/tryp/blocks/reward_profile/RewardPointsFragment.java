package com.rdev.tryp.blocks.reward_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import afu.org.checkerframework.checker.nullness.qual.Nullable;
import androidx.fragment.app.Fragment;

public class RewardPointsFragment extends Fragment implements View.OnClickListener {
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_reward, container, false);

        ImageView fab = root.findViewById(R.id.back_btn);
        fab.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View v) {
        ((ContentActivity) getActivity()).startFragment(ContentActivity.TYPE_REWARDS);
    }
}
