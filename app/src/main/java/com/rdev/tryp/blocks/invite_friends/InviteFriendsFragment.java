package com.rdev.tryp.blocks.invite_friends;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;

import java.util.Objects;

import androidx.fragment.app.Fragment;

public class InviteFriendsFragment extends Fragment implements View.OnClickListener {

    private View root;
    private ImageView backBtn, shareBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_invite_friends, container, false);

        initView();

        return root;
    }

    private void initView(){
        backBtn = root.findViewById(R.id.back_btn);
        shareBtn = root.findViewById(R.id.share_button);

        backBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_btn:
                ((ContentActivity) Objects.requireNonNull(getActivity())).popBackStack();
                break;
            case R.id.share_button:
                Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
