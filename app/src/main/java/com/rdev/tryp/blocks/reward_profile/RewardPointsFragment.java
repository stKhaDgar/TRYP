package com.rdev.tryp.blocks.reward_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import afu.org.checkerframework.checker.nullness.qual.Nullable;
import androidx.fragment.app.Fragment;

public class RewardPointsFragment extends Fragment implements View.OnClickListener {
    private View root;
    private RelativeLayout labelLayout;
    private ImageView fab;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_reward, container, false);

        initView();

        return root;
    }

    public void initView() {
        fab = root.findViewById(R.id.back_btn);
        labelLayout = root.findViewById(R.id.label_layout);

        fab.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                ((ContentActivity) getActivity()).startFragment(ContentActivity.TYPE_REWARDS);
                break;
        }
    }
}
