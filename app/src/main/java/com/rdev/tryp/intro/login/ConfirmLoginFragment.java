package com.rdev.tryp.intro.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.rdev.tryp.PinEntryEditText;
import com.rdev.tryp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.rdev.tryp.utils.Utils.closeKeyboard;
import static com.rdev.tryp.utils.Utils.showKeyboard;

public class ConfirmLoginFragment extends Fragment implements View.OnClickListener {
    ImageButton backBtn;
    ImageButton verifySmsBtn;
    PinEntryEditText pinEntryEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.confirm_layout, container, false);
        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        pinEntryEditText = v.findViewById(R.id.pin_edit_text);
        verifySmsBtn = v.findViewById(R.id.send_btn);
        verifySmsBtn.setOnClickListener(this);

        pinEntryEditText.requestFocus();
        showKeyboard(getContext());

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                ((LoginActivity) getActivity()).goBack();
                break;
            case R.id.send_btn:
                ((LoginActivity) getActivity()).verifySms(pinEntryEditText.getText().toString());
        }
        closeKeyboard(getContext());
    }
}
