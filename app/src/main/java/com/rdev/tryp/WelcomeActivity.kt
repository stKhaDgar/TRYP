package com.rdev.tryp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast

import com.rdev.tryp.intro.createAccount.CreateActivity
import com.rdev.tryp.login.LoginActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val PERMISSIONS_REQUEST = 1010
    }

    private lateinit var btnLogIn: AppCompatButton
    private lateinit var btnSignUp: AppCompatButton

    private var isPermissionsGranded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        btnLogIn = findViewById(R.id.login_btn)
        btnLogIn.setOnClickListener(this)
        btnSignUp = findViewById(R.id.sign_up_btn)
        btnSignUp.setOnClickListener(this)

        isPermissionsGranded = requestPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST) {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED)
                    return
            }

            isPermissionsGranded = true
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermissions(): Boolean {
        val tag = "tag"
        return if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v(tag, "Permission is revoked")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST)
            false
        } else {
            Log.v(tag, "Permission is granted")
            true
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

    override fun onClick(view: View) {
        if (!isPermissionsGranded) {
            Toast.makeText(this, "Please allow permissions", Toast.LENGTH_SHORT).show()
            isPermissionsGranded = requestPermissions()
            return
        }

        var intent: Intent? = null
        when (view.id) {
            R.id.login_btn -> intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            R.id.sign_up_btn -> intent = Intent(this@WelcomeActivity, CreateActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

}