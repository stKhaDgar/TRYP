package com.rdev.tryp.intro.createAccount

import android.content.Intent
import android.os.Bundle
import android.util.Log

import com.rdev.tryp.WelcomeActivity
import com.rdev.tryp.createAccount.EnterPhoneFragment
import com.rdev.tryp.createAccount.SignUpFragment
import com.rdev.tryp.login.LoginActivity
import com.rdev.tryp.network.ApiClient
import com.rdev.tryp.network.ApiService
import com.rdev.tryp.R
import com.rdev.tryp.model.CreateUser
import com.rdev.tryp.model.sign_up_response.SignUpResponse

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateActivity : AppCompatActivity() {
    val createUser = CreateUser()
    private lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createUser.country_code = "USA"
        createUser.dialing_code = "1"
        setContentView(R.layout.create_activity)
        fm = supportFragmentManager
        fm.beginTransaction().add(R.id.container, EnterPhoneFragment()).commit()

    }

    fun onFinish() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    fun goBack() {
        if (fm.backStackEntryCount != 0) {
            fm.popBackStack()
        } else {
            onFinish()
        }
    }

    fun createAccount() {
        fm.beginTransaction().replace(R.id.container, SignUpFragment()).addToBackStack("ssg").commit()
    }

    fun signUp() {
        val apiService = ApiClient.getInstance()?.create(ApiService::class.java)
        apiService?.createAccount(createUser)?.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful
                        && response.body() != null
                        && response.body()?.data != null
                        && response.body()?.data?.message != null) {

                    AlertDialog.Builder(this@CreateActivity)
                            .setMessage(response.body()?.data?.message)
                            .setPositiveButton(android.R.string.ok, null)
                            .setOnDismissListener {
                                val intent = Intent(this@CreateActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .show()
                } else {
                    AlertDialog.Builder(this@CreateActivity)
                            .setMessage("Something was wrong. Please check all fields and try again")
                            .setPositiveButton(android.R.string.ok, null)
                            .setCancelable(true)
                            .show()
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("tag", t.message)
            }
        })
        Log.d("tag", createUser.toString())
    }

}