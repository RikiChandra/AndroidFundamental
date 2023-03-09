package com.example.githubapp

import okhttp3.Interceptor
import com.example.githubapp.BuildConfig



class ApiConfig {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    private val authInterceptor = Interceptor {chain ->
        val req = chain.request()
        val secretKey = "token ${BuildConfig.API_KEY}"
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", secretKey)
            .build()
        chain.proceed(requestHeaders)
    }

    private val client = okhttp3.OkHttpClient.Builder().addInterceptor(authInterceptor).build()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}