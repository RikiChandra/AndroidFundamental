package com.example.githubapp

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("search/users")
    fun getUserSearch(@Query("q") login: String): Call<UserResponse>


    @GET("users/{login}")
    fun getUserDetail(@Path("login") login: String): Call<UserGithub>

}