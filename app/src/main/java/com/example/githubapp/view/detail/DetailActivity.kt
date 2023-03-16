package com.example.githubapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.UserGithub
import com.example.githubapp.data.entity.FavoriteEntity
import com.example.githubapp.databinding.ActivityDetailBinding
import com.example.githubapp.view.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var username: String = ""


    private var favoriteUser: FavoriteEntity? = null
    private lateinit var usersFactory : DetailViewModel
    private var buttonState:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersFactory = viewFactory(this@DetailActivity)

        val loadingProgressBar = findViewById<ProgressBar>(R.id.loadings)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, intent.getStringExtra("login") ?: "")
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        usersFactory.userDetail.observe(this){ detailUser ->
            val detailList = detailUser
            showDetailUser(detailList)
            favoriteUser = FavoriteEntity(detailUser.id, detailUser.login, detailUser.avatarUrl)
            usersFactory.getFavoriteAll().observe(this){ favoriteList ->
                if (favoriteList != null){
                    for (i in favoriteList){
                        if(detailUser.id == i.id){
                            buttonState = true
                            binding.fabAdd.setImageResource(R.drawable.baseline_favorite_24)
                        }
                    }
                }
            }

            binding.fabAdd.setOnClickListener {
                if (!buttonState){
                    insertFavorite(detailList)
                    binding.fabAdd.setImageResource(R.drawable.baseline_favorite_24)
                    buttonState = true
                } else {
                    usersFactory.delete(detailList.id)
                    binding.fabAdd.setImageResource(R.drawable.baseline_favorite_border_24)
                    buttonState = false
                    Toast.makeText(this, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show()
                }

            }


        }


        // Mendapatkan username dari intent
        username = intent.getStringExtra("login") ?: ""

        // Membuat instance ViewModel
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        // Mendapatkan data user detail dari API
        viewModel.getUserDetail(username)

        // Observer untuk memperbarui tampilan saat data diubah
        viewModel.userDetail.observe(this) { detailUser ->
            showDetailUser(detailUser)
            if (detailUser.name == null) {
                supportActionBar?.title = detailUser.login
            } else {
                supportActionBar?.title = detailUser.name
            }

        }

        // Observer untuk menampilkan loading
        viewModel.isLoading.observe(this) { isLoading ->
            loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }

    private fun showDetailUser(detail: UserGithub) {
        with(binding) {
            // Menampilkan data ke tampilan
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .into(profileImage)

            binding.TvNama.text = detail.name
            binding.TvUsername.text = detail.login
            followersTextView.text = resources.getString(R.string.jmlhFollowers, detail.followers)
            followingTextView.text = resources.getString(R.string.jmlhFollowing, detail.following)

        }
    }

    private fun viewFactory(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    private fun insertFavorite(detailList: UserGithub){
        favoriteUser.let {
            favoriteUser ->
            favoriteUser?.id = detailList.id
            favoriteUser?.login = detailList.login
            favoriteUser?.avatarUrl = detailList.avatarUrl
            usersFactory.insert(favoriteUser as FavoriteEntity)
            Toast.makeText(this, "Berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
        }
    }



    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}
