package com.example.githubapp.view.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.entity.FavoriteEntity
import com.example.githubapp.databinding.ListBinding
import com.example.githubapp.ultils.FavoriteDiffcallback
import com.example.githubapp.view.detail.DetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<FavoriteEntity>()


    fun setFavorite(favorites: List<FavoriteEntity>){
        val diffCallback = FavoriteDiffcallback(this.listFavorite, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(val binding: ListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteEntity) {
            with(binding){
                tvName.text = favorite.login
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("login", favorite.login)
                    itemView.context.startActivity(intent)
                }
            }

            Glide.with(binding.root.context)
                .load(favorite.avatarUrl)
                .into(binding.imgAvatar)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = listFavorite[position]
        holder.bind(favorites)
    }
}
