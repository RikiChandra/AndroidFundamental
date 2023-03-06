package com.example.githubapp

import okhttp3.Interceptor

class ApiConfig {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    val authInterceptor = Interceptor {chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", "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")
            .build()
        chain.proceed(requestHeaders)
    }

    val client = okhttp3.OkHttpClient.Builder().addInterceptor(authInterceptor).build()

    val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}