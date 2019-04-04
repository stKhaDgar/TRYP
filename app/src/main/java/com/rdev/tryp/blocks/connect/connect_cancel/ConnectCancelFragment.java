package com.rdev.tryp.blocks.connect.connect_cancel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ConnectCancelFragment extends Fragment implements View.OnClickListener {

    private View root;
    private ImageView backBtn, confirmBtn;
    private RadioGroup radioGroup;
    private RadioButton radioBtn1, radioBtn2, radioBtn3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_connect_cancel, container, false);

        initView();

        return root;
    }

    private void initView() {
        backBtn = root.findViewById(R.id.back_btn);
        confirmBtn = root.findViewById(R.id.confirm_button);
        radioGroup = root.findViewById(R.id.radio_group);
        radioBtn1 = root.findViewById(R.id.radio_btn_1);
        radioBtn2 = root.findViewById(R.id.radio_btn_2);
        radioBtn3 = root.findViewById(R.id.radio_btn_3);

        backBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                ((ContentActivity) Objects.requireNonNull(getActivity())).popBackStack();
                break;
            case R.id.confirm_button:
                String idTemp = ((ContentActivity) getActivity()).currentRide.getId();
                ((ContentActivity) getActivity()).database.cancelRide(idTemp, getCurrentChecked());
                ((ContentActivity) Objects.requireNonNull(getActivity())).clearBackStack();
                ((ContentActivity)getActivity()).clearMap();
                ((ContentActivity)getActivity()).initMap();
                break;
        }
    }

    private String getCheckedReason(int id){
        switch(id){
            case R.id.radio_btn_1:
                return radioBtn1.getText().toString();
            case R.id.radio_btn_2:
                return radioBtn2.getText().toString();
            case R.id.radio_btn_3:
                return radioBtn3.getText().toString();
        }
        return radioBtn1.getText().toString();
    }

    private String getCurrentChecked(){
        if(radioBtn1.isChecked()){
            return radioBtn1.getText().toString();
        } else if(radioBtn2.isChecked()){
            return radioBtn2.getText().toString();
        } else if(radioBtn3.isChecked()){
            return radioBtn3.getText().toString();
        } else {
            return null;
        }
    }

}
