package com.example.githubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubapp.data.entity.FavoriteEntity
import com.example.githubapp.data.room.FavoriteDao
import com.example.githubapp.data.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application : Application) {

    private val favoriteDao : FavoriteDao
    private val excutors : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = FavoriteDatabase.getDatabase(application)
        favoriteDao = database.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = favoriteDao.getAllFavorite()

    fun insert(user : FavoriteEntity){
        excutors.execute {
            favoriteDao.insertFavorite(user)
        }
    }

    fun delete(id : Int){
        excutors.execute {
            favoriteDao.deleteFavorite(id)
        }
    }

}