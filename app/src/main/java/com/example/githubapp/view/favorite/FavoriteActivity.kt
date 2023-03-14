package com.example.githubapp.view.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.databinding.ActivityFavoriteBinding
import com.example.githubapp.view.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteFactory: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        favoriteFactory = viewFactory(this@FavoriteActivity)
        favoriteFactory.getFavoriteAll().observe(this){ favoriteList ->
            if (favoriteList != null){
                adapter.setFavorite(favoriteList)
            }
        }

        adapter = FavoriteAdapter()
        binding?.RvFav?.layoutManager = LinearLayoutManager(this)
        binding?.RvFav?.setHasFixedSize(false)
        binding?.RvFav?.adapter = adapter


    }


    private fun viewFactory(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}