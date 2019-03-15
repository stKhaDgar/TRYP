package com.rdev.tryp.trip.tryp_car;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.model.DriversItem;
import com.rdev.tryp.trip.TripFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class TrypCarFragment extends Fragment {

    private View v;

    private DriversItem driver;
    private TextView tryp_type_tv, num_of_passangers, num_of_baggage, num_of_door_tv, price_tv;
    private ImageView car_iv;
    public static final String TYPE_TRYP = "TRYP";
    public static final String TYPE_TRYP_EXTRA = "TRYP Extra";
    public static final String TYPE_TRYP_PRIME = "TRYP Prime";
    public static final String TYPE_TRYP_ASSIST = "TRYP Assist";

    @SuppressLint("ValidFragment")
    public TrypCarFragment(DriversItem driver) {
        this.driver = driver;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tryp_car_fragment, container, false);
        tryp_type_tv = v.findViewById(R.id.category_tv);
        car_iv = v.findViewById(R.id.car_iv);
        num_of_door_tv = v.findViewById(R.id.num_of_door_tv);
        num_of_baggage = v.findViewById(R.id.num_of_baggage);
        num_of_passangers = v.findViewById(R.id.num_of_passangers);
        price_tv = v.findViewById(R.id.price_tv);

//        Glide.with(getContext()).load(driver.getVehicle().getImage()).into(car_iv);
        car_iv.setImageDrawable(ContextCompat.getDrawable(getContext(),
                TrypCarFragment.getImageByType(driver.getCategory())));
        tryp_type_tv.setText(driver.getCategory());
        num_of_door_tv.setText("4/4"); //TODO: replace from driver
        num_of_passangers.setText("" + driver.getMaxPassenger());
        price_tv.setText("$" + String.valueOf(driver.getFare()));
        num_of_baggage.setText("" + driver.getMaxLuggage());

        CardView trypNowBtn = v.findViewById(R.id.tryp_now_btn);
        trypNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TripFragment.orderTrip(getActivity(), driver, ContentActivity.tripFrom, ContentActivity.tripTo);
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

    public static int getImageByType(String type) {
        switch (type) {
            case TYPE_TRYP:
                return R.drawable.tryp_car;
            case TYPE_TRYP_ASSIST:
                return R.drawable.tryp_asist;
            case TYPE_TRYP_EXTRA:
                return R.drawable.tryp_extra;
            case TYPE_TRYP_PRIME:
                return R.drawable.tryp_prime;
        }
        return R.drawable.tryp_car;
    }
}
