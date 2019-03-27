package com.rdev.tryp.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rdev.tryp.ContentActivity;
import com.rdev.tryp.R;
import com.rdev.tryp.blocks.forme.edit_addresses.AddressNetworkService;
import com.rdev.tryp.firebaseDatabase.utils.TrypDatabase;
import com.rdev.tryp.intro.manager.AccountManager;
import com.rdev.tryp.model.LoginModel;
import com.rdev.tryp.model.LoginResponse;
import com.rdev.tryp.model.RealmCallback;
import com.rdev.tryp.model.RealmUtils;
import com.rdev.tryp.model.UserPhoneNumber;
import com.rdev.tryp.model.login_response.VerifySmsResponse;
import com.rdev.tryp.network.ApiClient;
import com.rdev.tryp.network.ApiService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("NullableProblems")
public class LoginActivity extends AppCompatActivity {
    FragmentManager fm;
    ApiService apiService;
    LoginModel loginModel;
    UserPhoneNumber number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.login_container, new LoginFragment())
                .commit();

        apiService = ApiClient.getInstance().create(ApiService.class);
        number = new UserPhoneNumber();
        number.setCountry_code("USA");
        number.setDialing_code("1");
    }

    public void onSendCode() {
        if (isNetworkOnline() && number.getPhone_number().length() >= 7 && number.getPhone_number().length() <= 13) {
            apiService.sendSms(number).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    fm.beginTransaction().replace(R.id.login_container, new ConfirmLoginFragment())
                            .addToBackStack("login").commit();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else if (number.getPhone_number().length() < 7 || number.getPhone_number().length() > 13) {
            Toast.makeText(LoginActivity.this, "Phone number must have 7-13 digits", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack() {
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

    public void verifySms(String verification_code) {
        loginModel = new LoginModel(number);
        loginModel.setVerification_code(verification_code);
        if (isNetworkOnline() && loginModel.getVerification_code().length() == 4) {
            apiService.verifySms(loginModel).enqueue(new Callback<VerifySmsResponse>() {
                @Override
                public void onResponse(Call<VerifySmsResponse> call, Response<VerifySmsResponse> response) {
                    VerifySmsResponse body = response.body();
                    Log.i("response", response.toString());
                    Log.i("response", response.body().toString());
                    if (body == null || body.getData() == null) {
                        Toast.makeText(LoginActivity.this, "Wrong code. Please try again", Toast.LENGTH_LONG).show();
                        return;
                    }

                    AddressNetworkService.initFavoriteAddresses();

                    AccountManager.getInstance().signIn(body.getData().getUsers().getUserId());

                    new RealmUtils(getApplicationContext(), new RealmCallback() {
                        @Override
                        public void dataUpdated() {

                            new TrypDatabase().updateUser(body.getData().getUsers(), LoginActivity.this, new RealmCallback() {
                                @Override
                                public void dataUpdated() {
                                    Intent intent = new Intent(LoginActivity.this, ContentActivity.class);
                                    intent.putExtra("tag", "f");
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void error() {
                                    Intent intent = new Intent(LoginActivity.this, ContentActivity.class);
                                    intent.putExtra("tag", "f");
                                    startActivity(intent);
                                    finish();
                                }
                            });

                        }

                        @Override
                        public void error() {
                            Intent intent = new Intent(LoginActivity.this, ContentActivity.class);
                            intent.putExtra("tag", "f");
                            startActivity(intent);
                            finish();
                        }

                    }).pushUser(body.getData().getUsers());
                }

                @Override
                public void onFailure(Call<VerifySmsResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else if (loginModel.getVerification_code().length() < 4) {
            Toast.makeText(LoginActivity.this, "Please enter verification code", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }
}
