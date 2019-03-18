package com.rdev.tryp.blocks.reward_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
        root.findViewById(R.id.btnWithdraw).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                ((ContentActivity) getActivity()).startFragment(ContentActivity.TYPE_REWARDS);
                break;
            case R.id.btnWithdraw:
                ((ContentActivity) getActivity()).startFragment(ContentActivity.TYPE_REWARDS_WITHDRAW);
                break;
        }
    }
}
