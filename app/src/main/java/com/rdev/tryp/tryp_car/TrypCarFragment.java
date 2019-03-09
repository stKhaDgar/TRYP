package com.rdev.tryp.tryp_car;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdev.tryp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class TrypCarFragment extends Fragment {

    private View v;

    int type;
    TextView tryp_type_tv;
    ImageView assist_iv;
    public static final int TYPE_TRYP = 0;
    public static final int TYPE_TRYP_EXTRA = 1;
    public static final int TYPE_TRYP_PRIME = 2;
    public static final int TYPE_TRYP_ASSIST = 3;

    @SuppressLint("ValidFragment")
    public TrypCarFragment(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tryp_car_fragment, container, false);
        tryp_type_tv = v.findViewById(R.id.category_tv);
        assist_iv = v.findViewById(R.id.assist_iv);
        switch (type) {
            case TYPE_TRYP:
                tryp_type_tv.setText("Tryp");
                break;
            case TYPE_TRYP_ASSIST:
                tryp_type_tv.setText("Tryp Assist");
                assist_iv.setVisibility(View.VISIBLE);
                break;
            case TYPE_TRYP_EXTRA:
                tryp_type_tv.setText("Tryp Extra");
                break;
            case TYPE_TRYP_PRIME:
                tryp_type_tv.setText("Tryp Prime");
                break;
        }

        CardView trypNowBtn = v.findViewById(R.id.tryp_now_btn);
        trypNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialod("Ride request Successful", "Your ride request succesffully send");
            }
        });
        return v;
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
