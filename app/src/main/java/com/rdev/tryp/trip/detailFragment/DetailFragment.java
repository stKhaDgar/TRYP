package com.rdev.tryp.trip.detailFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.model.DriversItem;
import com.rdev.tryp.trip.TripFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class DetailFragment extends Fragment  implements View.OnClickListener {

    private DriversItem driver;
    private TextView tryp_type_tv, num_of_passangers, num_of_baggage, num_of_door_tv, price_tv, name_tv, car_type_tv;
    private ImageView car_iv, user_iv;
    private CardView moveCard;
    private DetailsAnimation animation;
    private RelativeLayout mainRelativeLayout;
    private CardView trypNowBtn, imageCard;

    @SuppressLint("ValidFragment")
    public DetailFragment(DriversItem driver) {
        this.driver = driver;
    }

    private View.OnClickListener listener;
    private View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.detail_fragment,container,false);
        trypNowBtn = v.findViewById(R.id.tryp_now_btn);

        tryp_type_tv = v.findViewById(R.id.category_tv);
        car_iv = v.findViewById(R.id.car_iv);
        num_of_door_tv = v.findViewById(R.id.num_of_door_tv);
        num_of_baggage = v.findViewById(R.id.num_of_baggage);
        num_of_passangers = v.findViewById(R.id.num_of_passangers);
        price_tv = v.findViewById(R.id.price_tv);
        name_tv = v.findViewById(R.id.name_tv);
        user_iv = v.findViewById(R.id.user_iv);
        car_type_tv = v.findViewById(R.id.car_type);
        moveCard = v.findViewById(R.id.content_move_card);
        mainRelativeLayout =v.findViewById(R.id.all_content_relative_layout);
        imageCard = v.findViewById(R.id.image_card);

        Glide.with(getContext()).load(driver.getVehicle().getImage()).into(car_iv);
        tryp_type_tv.setText(driver.getCategory());
        num_of_door_tv.setText("4/4"); //TODO: replace from driver
        num_of_passangers.setText("" + driver.getMaxPassenger());
        price_tv.setText("$" + String.valueOf(driver.getFare()));
        num_of_baggage.setText("" + driver.getMaxLuggage());
        name_tv.setText(driver.getDriver().getFirstName() + driver.getDriver().getLastName());
        Glide.with(getContext()).load(driver.getDriver().getImage()).into(user_iv);
        car_type_tv.setText(driver.getType());
        trypNowBtn.setOnClickListener(this);
        mainRelativeLayout.setOnClickListener(this);

        animation = new DetailsAnimation(moveCard, user_iv, imageCard);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tryp_now_btn:
                TripFragment.orderTrip(getContext(), driver, ContentActivity.tripFrom, ContentActivity.tripTo);
                break;
            case R.id.all_content_relative_layout:
                animation.start();
                break;
        }
    }
}
