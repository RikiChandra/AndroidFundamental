package com.example.githubapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("search/users")
    fun getUserSearch(@Query("q") login: String): Call<UserResponse>


    @GET("users/{login}")
    fun getUserDetail(@Path("login") login: String): Call<UserGithub>

    @GET("users/{login}/followers")
    fun getUserFollowers(@Path("login") login: String): Call<List<UserGithub>>

    @GET("users/{login}/following")
    fun getUserFollowing(@Path("login") login: String): Call<List<UserGithub>>

}