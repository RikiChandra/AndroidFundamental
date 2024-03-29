package com.example.githubapp.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapp.view.detail.DetailActivity
import com.example.githubapp.UserGithub
import com.example.githubapp.databinding.ListBinding


class AdapterList(public val listUser: ArrayList<UserGithub>) : RecyclerView.Adapter<AdapterList.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        with(holder.binding) {
            Glide.with(holder.binding.root.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(55, 55))
                .into(holder.binding.imgAvatar)
            holder.binding.tvName.text = user.login
            holder.binding.root.setOnClickListener {
                val intent = Intent(holder.binding.root.context, DetailActivity::class.java)
                intent.putExtra("login", user.login)
                intent.putExtra("html_url", user.htmlUrl)
                holder.binding.root.context.startActivity(intent)
            }


        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }


    class ListViewHolder(val binding: ListBinding) : RecyclerView.ViewHolder(binding.root)


}