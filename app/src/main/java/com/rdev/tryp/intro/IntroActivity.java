package com.rdev.tryp.intro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.WelcomeActivity;
import com.rdev.tryp.intro.manager.AccountManager;
import com.rdev.tryp.utils.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        PreferenceManager.init(this);

        if (AccountManager.getInstance().isUserSignIn()) {
                    Intent intent = new Intent(IntroActivity.this, ContentActivity.class);
                    intent.putExtra("tag", "f");
                    startActivity(intent);
                    finish();
        } else {
                    Intent intent = new Intent(IntroActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
