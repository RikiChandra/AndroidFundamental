package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailModelView
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingProgressBar = findViewById<ProgressBar>(R.id.loadings)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, intent.getStringExtra("login") ?: "")
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Mendapatkan username dari intent
        username = intent.getStringExtra("login") ?: ""

        // Membuat instance ViewModel
        viewModel = ViewModelProvider(this).get(DetailModelView::class.java)

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
            followersTextView.text = "${detail.followers} Followers "
            followingTextView.text = "${detail.following} Following"

        }
    }



    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}
