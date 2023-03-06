package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailModelView
    private var username: String = ""

    private lateinit var Tvname : TextView
    private lateinit var Tvusername : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingProgressBar = findViewById<ProgressBar>(R.id.loadings)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Mendapatkan username dari intent
        username = intent.getStringExtra("login") ?: ""

        // Membuat instance ViewModel
        viewModel = ViewModelProvider(this).get(DetailModelView::class.java)

        // Mendapatkan data user detail dari API
        viewModel.getUserDetail(username)

        // Observer untuk memperbarui tampilan saat data diubah
        viewModel.userDetail.observe(this, { detailUser ->
            showDetailUser(detailUser)
            supportActionBar?.title = detailUser.name
        })


        // Observer untuk menampilkan loading
        viewModel.isLoading.observe(this, { isLoading ->
            loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

    }

    private fun showDetailUser(detail: UserGithub) {
        with(binding) {
            // Menampilkan data ke tampilan
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .into(profileImage)

            binding.TvNama.text = detail.name
            binding.TvUsername.text = detail.login
            followersTextView.text = "Followers: ${detail.followers}"
            followingTextView.text = "Following: ${detail.following}"

        }
    }



    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}
