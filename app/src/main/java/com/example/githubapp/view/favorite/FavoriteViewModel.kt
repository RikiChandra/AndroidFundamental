package com.example.githubapp.view.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.data.entity.FavoriteEntity
import com.example.githubapp.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val repository = FavoriteRepository(application)

    fun getFavoriteAll(): LiveData<List<FavoriteEntity>> = repository.getAllFavorite()

}