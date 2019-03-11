package com.rdev.tryp.navigation;

import android.content.Intent;
import android.os.Bundle;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.intro.IntroActivity;
import com.rdev.tryp.tryp_later.TrypLaterActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationActivity extends AppCompatActivity implements NavigationAdapter.NavListener {
    RecyclerView navigation_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        navigation_rv = findViewById(R.id.navigation_rv);
        navigation_rv.setAdapter(new NavigationAdapter(this));
        navigation_rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onNavigationClick(int pos) {
        Intent intent = null;
        if (pos == 0) {
            intent = new Intent(this, IntroActivity.class);
        } else if (pos == 1) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("tag", "tryp");
        } else if (pos == 2) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("tag", "detail");
        } else if (pos == 3) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("tag", "f");
        } else if (pos == 4) {
            intent = new Intent(this, TrypLaterActivity.class);
        } else if (pos == 5) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("tag", "car");
        } else if (pos == 6) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("tag", "confirm");
        } else if (pos == 7) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("tag", "set");
        } else if (pos == 8) {
            intent = new Intent(this, ContentActivity.class);
            intent.putExtra("tag", "connect");
        }
        startActivity(intent);
    }
}
