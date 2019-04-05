package com.rdev.tryp.intro

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.WelcomeActivity
import com.rdev.tryp.intro.manager.AccountManager
import com.rdev.tryp.utils.PreferenceManager

import androidx.appcompat.app.AppCompatActivity


class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        PreferenceManager.init(this)

        if (AccountManager.getInstance()?.isUserSignIn == true) {
            val intent = Intent(this@IntroActivity, ContentActivity::class.java)
            intent.putExtra("tag", "f")
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@IntroActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

}