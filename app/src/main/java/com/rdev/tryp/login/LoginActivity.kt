package com.rdev.tryp.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.forme.edit_addresses.AddressNetworkService
import com.rdev.tryp.firebaseDatabase.utils.TrypDatabase
import com.rdev.tryp.intro.manager.AccountManager
import com.rdev.tryp.model.LoginModel
import com.rdev.tryp.model.LoginResponse
import com.rdev.tryp.model.RealmCallback
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.model.UserPhoneNumber
import com.rdev.tryp.model.login_response.VerifySmsResponse
import com.rdev.tryp.network.ApiClient
import com.rdev.tryp.network.ApiService

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var fm: FragmentManager
    private lateinit var apiService: ApiService
    private lateinit var loginModel: LoginModel
    lateinit var number: UserPhoneNumber

    private val isNetworkOnline: Boolean
        get() {
            var status = false
            try {
                val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                var netInfo: NetworkInfo? = cm.getNetworkInfo(0)
                if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED) {
                    status = true
                } else {
                    netInfo = cm.getNetworkInfo(1)
                    if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED)
                        status = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

            return status

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        fm = supportFragmentManager
        fm.beginTransaction().add(R.id.login_container, LoginFragment())
                .commit()

        apiService = ApiClient.getInstance().create(ApiService::class.java)
        number = UserPhoneNumber()
        number.country_code = "USA"
        number.dialing_code = "1"
    }

    fun onSendCode() {
        if (isNetworkOnline && number.phone_number.length >= 7 && number.phone_number.length <= 13) {
            apiService.sendSms(number).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    fm.beginTransaction().replace(R.id.login_container, ConfirmLoginFragment())
                            .addToBackStack("login").commit()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })
        } else if (number.phone_number.length < 7 || number.phone_number.length > 13) {
            Toast.makeText(this@LoginActivity, "Phone number must have 7-13 digits", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@LoginActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun goBack() {
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        }
    }

    fun verifySms(verification_code: String) {
        loginModel = LoginModel(number)
        loginModel.verification_code = verification_code
        if (isNetworkOnline && loginModel.verification_code.length == 4) {
            apiService.verifySms(loginModel).enqueue(object : Callback<VerifySmsResponse> {
                override fun onResponse(call: Call<VerifySmsResponse>, response: Response<VerifySmsResponse>) {
                    val body = response.body()
                    Log.i("response", response.toString())
                    Log.i("response", response.body()?.toString())

                    if (body == null || body.data == null) {
                        Toast.makeText(this@LoginActivity, "Wrong code. Please try again", Toast.LENGTH_LONG).show()
                        return
                    }

                    AddressNetworkService.initFavoriteAddresses()

                    AccountManager.getInstance()?.signIn(body.data.users.userId)

                    RealmUtils(applicationContext, object : RealmCallback {
                        override fun dataUpdated() {

                            TrypDatabase().getOrCreateUser(body.data.users, this@LoginActivity, object : RealmCallback {
                                override fun dataUpdated() {
                                    val intent = Intent(this@LoginActivity, ContentActivity::class.java)
                                    intent.putExtra("tag", "f")
                                    startActivity(intent)
                                    finish()
                                }

                                override fun error() {
                                    val intent = Intent(this@LoginActivity, ContentActivity::class.java)
                                    intent.putExtra("tag", "f")
                                    startActivity(intent)
                                    finish()
                                }
                            })
                        }

                        override fun error() {
                            val intent = Intent(this@LoginActivity, ContentActivity::class.java)
                            intent.putExtra("tag", "f")
                            startActivity(intent)
                            finish()
                        }

                    }).pushUser(body.data.users)
                }

                override fun onFailure(call: Call<VerifySmsResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })
        } else if (loginModel.verification_code.length < 4) {
            Toast.makeText(this@LoginActivity, "Please enter verification code", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@LoginActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

}
