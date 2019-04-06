package com.rdev.tryp.network

import java.net.CookieManager

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Alexey Matrosov on 06.03.2019.
 */


object ApiClient {

    private const val BASE_URL = "http://52.14.204.198/"
    private var instance: Retrofit? = null

    fun getInstance(): Retrofit? {
        if (instance == null)
            instance = init()

        return instance
    }

    private fun init(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        // Init cookie manager
        val cookieHandler = CookieManager()

        // Init Http Client
        val client = OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(cookieHandler))
                .addInterceptor(interceptor)
                //                .connectTimeout(10, TimeUnit.SECONDS)
                //                .writeTimeout(10, TimeUnit.SECONDS)
                //                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        // Init Retrofit
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

}