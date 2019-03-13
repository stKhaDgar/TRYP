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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class RewardProfileFragment extends Fragment implements View.OnClickListener {
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_reward_profile, container, false);

        ImageView fab = root.findViewById(R.id.back_btn);
        fab.setOnClickListener(this);
        CardView cardView = root.findViewById(R.id.top_card_view);
        cardView.setBackgroundResource(R.drawable.card_view_bg);

        CardView rewardPoints = root.findViewById(R.id.rewards_points_card_view);
        rewardPoints.setOnClickListener(this);
        CardView credits = root.findViewById(R.id.credits_card_view);
        credits.setOnClickListener(this);

        ImageView settings = root.findViewById(R.id.settings_img);
        settings.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                ((ContentActivity) getActivity()).goHome();
                break;
            case R.id.credits_card_view:
                Toast.makeText(getContext(), "clicked credits", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rewards_points_card_view:
                ((ContentActivity) getActivity()).startFragment(ContentActivity.TYPE_REWARD_POINTS);
                break;
            case R.id.settings_img:
                Toast.makeText(getContext(), "clicked settings", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
