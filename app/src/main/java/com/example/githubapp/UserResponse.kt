package com.example.githubapp

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val git : List<UserGithub>,


)

data class UserGithub(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

)

