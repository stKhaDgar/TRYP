package com.rdev.tryp.tryp_later;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.rdev.tryp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TrypLaterActivity extends AppCompatActivity implements View.OnClickListener {
    CardView dailyCard, timeCard, weekCard, monthCard;
    CardView checkedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryp_later);
        dailyCard = findViewById(R.id.materialCardView8);
        timeCard = findViewById(R.id.materialCardView9);
        weekCard = findViewById(R.id.materialCardView10);
        monthCard = findViewById(R.id.materialCardView11);

        dailyCard.setOnClickListener(this);
        timeCard.setOnClickListener(this);
        weekCard.setOnClickListener(this);
        monthCard.setOnClickListener(this);
        checkedCard = dailyCard;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onClick(View view) {
    }

}
