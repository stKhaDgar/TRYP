package com.rdev.tryp.createAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rdev.tryp.PickerPhoneDialog;
import com.rdev.tryp.R;
import com.rdev.tryp.SelectCountryListener;
import com.rdev.tryp.intro.createAccount.CreateActivity;
import com.rdev.tryp.model.CreateUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static com.rdev.tryp.utils.Utils.getCountryCode;
import static com.rdev.tryp.utils.Utils.getCountryName;
import static com.rdev.tryp.utils.Utils.getDialingCode;

public class EnterPhoneFragment extends Fragment implements View.OnClickListener {
    ImageButton back_btn;
    CardView create_account_card;
    EditText phone_et;
    CardView phonePickerCardView;
    TextView country_phone_tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.enter_phone, container, false);
        create_account_card = v.findViewById(R.id.create_account_card);
        create_account_card.setOnClickListener(this);
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        phone_et = v.findViewById(R.id.phone_et);
        phonePickerCardView = v.findViewById(R.id.cardView2);
        phonePickerCardView.setOnClickListener(this);
        country_phone_tv = v.findViewById(R.id.country_phone_tv);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                ((CreateActivity) getActivity()).onFinish();
                break;
            case R.id.create_account_card:
                if (phone_et.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                } else {
                    ((CreateActivity) getActivity()).createUser.setPhone_number(phone_et.getText().toString());
                    ((CreateActivity) getActivity()).createAccount();
                }
                break;
            case R.id.cardView2:
                final PickerPhoneDialog dialog = new PickerPhoneDialog();
                dialog.showDialog(getContext(), new SelectCountryListener() {
                    @Override
                    public void onSelect(String data) {
                        dialog.hideDialog();
                        CreateUser number = ((CreateActivity) getActivity()).createUser;
                        number.setDialing_code(getDialingCode(data));
                        number.setCountry_code(getCountryCode(data));
                        country_phone_tv.setText(getCountryName(data) + " (+" + number.getDialing_code() + ")");

                    }
                });
                break;
        }
    }
}
