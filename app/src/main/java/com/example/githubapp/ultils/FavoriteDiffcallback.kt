package com.example.githubapp.ultils

import androidx.recyclerview.widget.DiffUtil
import com.example.githubapp.data.entity.FavoriteEntity

class FavoriteDiffcallback(private val OldFav: List<FavoriteEntity>, private val NewFav: List<FavoriteEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return OldFav.size
    }

    override fun getNewListSize(): Int {
        return NewFav.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return OldFav[oldItemPosition].id == NewFav[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = OldFav[oldItemPosition]
        val newList = NewFav[newItemPosition]
        return oldList.login == newList.login && oldList.avatarUrl == newList.avatarUrl
    }
}
