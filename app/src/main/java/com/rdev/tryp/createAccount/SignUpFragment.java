package com.rdev.tryp.createAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rdev.tryp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    ImageButton back_btn;
    CardView sign_up_card;
    EditText sign_up_first_name_et;
    EditText sign_up_last_name_et;
    EditText sign_up_email_et;
    EditText sign_up_reffered_et;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_fragment, container, false);
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        sign_up_card = v.findViewById(R.id.sign_up_card);
        sign_up_card.setOnClickListener(this);
        sign_up_first_name_et = v.findViewById(R.id.sign_up_first_name_et);
        sign_up_last_name_et = v.findViewById(R.id.sign_up_last_name_et);
        sign_up_email_et = v.findViewById(R.id.sign_up_email_et);
        sign_up_reffered_et = v.findViewById(R.id.sign_up_reffered_et);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                ((CreateActivity) getActivity()).goBack();
                break;
            case R.id.sign_up_card:
                ((CreateActivity) getActivity()).createUser.setLast_name(sign_up_last_name_et.getText().toString());
                ((CreateActivity) getActivity()).createUser.setFirst_name(sign_up_first_name_et.getText().toString());
                ((CreateActivity) getActivity()).createUser.setEmail(sign_up_email_et.getText().toString());
                ((CreateActivity) getActivity()).createUser.setRef_code(sign_up_reffered_et.getText().toString());
                ((CreateActivity) getActivity()).signUp();
                break;

        }
    }
}
