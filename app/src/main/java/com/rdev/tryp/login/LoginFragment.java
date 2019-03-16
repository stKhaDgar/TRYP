package com.rdev.tryp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rdev.tryp.PickerPhoneDialog;
import com.rdev.tryp.R;
import com.rdev.tryp.SelectCountryListener;
import com.rdev.tryp.WelcomeActivity;
import com.rdev.tryp.intro.createAccount.CreateActivity;
import com.rdev.tryp.model.UserPhoneNumber;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static com.rdev.tryp.utils.Utils.getCountryCode;
import static com.rdev.tryp.utils.Utils.getCountryName;
import static com.rdev.tryp.utils.Utils.getDialingCode;

public class LoginFragment extends Fragment implements View.OnClickListener {
    ImageButton backBtn;
    TextView sign_up_tv;
    CardView send_code_card;
    EditText phone_number_et;
    CardView phone_card;
    TextView countrTv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        countrTv = v.findViewById(R.id.country_tv);
        sign_up_tv = v.findViewById(R.id.sign_up_tv);
        sign_up_tv.setOnClickListener(this);
        send_code_card = v.findViewById(R.id.send_code_card);
        send_code_card.setOnClickListener(this);
        phone_number_et = v.findViewById(R.id.phone_number_et);
        phone_card = v.findViewById(R.id.cardView2);
        phone_card.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                startActivity(new Intent(getContext(), WelcomeActivity.class));
                getActivity().finish();
                break;
            case R.id.sign_up_tv:
                startActivity(new Intent(getContext(), CreateActivity.class));
                getActivity().finish();
                break;
            case R.id.send_code_card:
                ((LoginActivity) getActivity()).number.setPhone_number(phone_number_et.getText().toString());
                ((LoginActivity) getActivity()).onSendCode();
                break;
            case R.id.cardView2:
                final PickerPhoneDialog dialog = new PickerPhoneDialog();
                dialog.showDialog(getContext(), new SelectCountryListener() {
                    @Override
                    public void onSelect(String data) {
                        dialog.hideDialog();
                        UserPhoneNumber number = ((LoginActivity) getActivity()).number;
                        number.setDialing_code(getDialingCode(data));
                        number.setCountry_code(getCountryCode(data));
                        String phone = getCountryName(data) + " (+" + number.getDialing_code() + ")";
                        countrTv.setText(phone);

                    }
                });
                break;
        }
    }

}
