package com.rdev.tryp.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rdev.tryp.PinEntryEditText;
import com.rdev.tryp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        showKeyboard();

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
        closeKeyboard();
    }

    private void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
