package com.example.githubapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavoriteEntity(
    @PrimaryKey
    var id : Int,

    @ColumnInfo(name = "login")
    var login : String,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl : String,

    @ColumnInfo(name = "html_url")
    var htmlUrl : String? = null,

)
