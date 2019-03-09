package com.rdev.tryp.detailFragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rdev.tryp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private View.OnClickListener listener;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_fragment,container,false);
        CardView trypNowBtn = view.findViewById(R.id.tryp_now_btn);
        trypNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialod("Ride request Successful", "Your ride request succesffully send");

            }
        });
        return view;
    }

    AlertDialog dialog;
    private void showAlertDialod(String title, String message) {

        TextView dialog_title_tv;
        ImageButton back_btn;
        TextView dialog_msg_tv;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.alert_dialog, null);
        dialog_msg_tv = v.findViewById(R.id.dialog_message);
        back_btn = v.findViewById(R.id.dialog_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog_title_tv = v.findViewById(R.id.dialog_title);
        dialog = new AlertDialog.Builder(getContext())
                .setView(v).create();
        dialog.show();
        dialog_title_tv.setText(title);
        dialog_msg_tv.setText(message);
    }

}
