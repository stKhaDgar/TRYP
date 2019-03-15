package com.rdev.tryp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ConnectFragment extends Fragment implements View.OnClickListener {

    private View root;
    private ImageView backBtn, cancelButton, supportIv, shareRideIv;
    private TextView supportTv, shareRideTv;
    private CardView contactCv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_connect, container, false);

        initView();

        return root;
    }

    public void initView() {
        backBtn = root.findViewById(R.id.back_btn);
        cancelButton = root.findViewById(R.id.cancel_btn);
        shareRideIv = root.findViewById(R.id.share_ride_iv);
        shareRideTv = root.findViewById(R.id.share_ride_tv);
        supportIv = root.findViewById(R.id.support_iv);
        supportTv = root.findViewById(R.id.support_tv);
        contactCv = root.findViewById(R.id.contact_cv);

        backBtn.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        shareRideTv.setOnClickListener(this);
        shareRideIv.setOnClickListener(this);
        supportTv.setOnClickListener(this);
        supportIv.setOnClickListener(this);
        contactCv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                ((ContentActivity)getActivity()).popBackStack();
                ((ContentActivity)getActivity()).clearMap();
                ((ContentActivity)getActivity()).initMap();
                break;
            case R.id.cancel_btn:
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_ride_iv:
            case R.id.share_ride_tv:
                Toast.makeText(getContext(), "share ride", Toast.LENGTH_SHORT).show();
                break;
            case R.id.support_iv:
            case R.id.support_tv:
                Toast.makeText(getContext(), "support", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact_cv:
                Toast.makeText(getContext(), "Contact", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
