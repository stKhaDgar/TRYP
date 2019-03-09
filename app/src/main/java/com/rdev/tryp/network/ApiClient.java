package com.rdev.tryp.network;

import java.net.CookieHandler;
import java.net.CookieManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexey Matrosov on 06.03.2019.
 */
public class ApiClient {
    private static final String BASE_URL = "http://52.14.204.198/";

    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null)
            instance = init();

        return instance;
    }

    private static Retrofit init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Init cookie manager
        CookieHandler cookieHandler = new CookieManager();

        // Init Http Client
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .addInterceptor(interceptor)
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Init Retrofit
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
