package com.rdev.tryp.intro.createAccount;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rdev.tryp.WelcomeActivity;
import com.rdev.tryp.intro.login.LoginActivity;
import com.rdev.tryp.network.ApiClient;
import com.rdev.tryp.network.ApiService;
import com.rdev.tryp.R;
import com.rdev.tryp.model.CreateUser;
import com.rdev.tryp.model.sign_up_response.SignUpResponse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity {
    CreateUser createUser;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createUser = new CreateUser();
        createUser.setCountry_code("USA");
        createUser.setDialing_code("1");
        setContentView(R.layout.create_activity);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.container, new EnterPhoneFragment()).commit();

    }

    public void onFinish() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    public void goBack() {
        if (fm.getBackStackEntryCount() != 0) {
            fm.popBackStack();
        }else {
           onFinish();
        }
    }

    public void createAccount() {
        fm.beginTransaction().replace(R.id.container, new SignUpFragment()).addToBackStack("ssg").commit();
    }

    public void signUp() {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        apiService.createAccount(createUser).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getData() != null
                        && response.body().getData().getMessage() != null) {

                    new AlertDialog.Builder(CreateActivity.this)
                        .setMessage(response.body().getData().getMessage())
                        .setPositiveButton(android.R.string.ok, null)
                        .setOnDismissListener(dialog1 -> {
                            Intent intent = new Intent(CreateActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .show();
                } else {
                   new AlertDialog.Builder(CreateActivity.this)
                        .setMessage("Something was wrong. Please check all fields and try again")
                        .setPositiveButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
        Log.d("tag", createUser.toString());
    }
}
