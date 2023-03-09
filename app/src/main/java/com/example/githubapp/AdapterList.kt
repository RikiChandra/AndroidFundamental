package com.example.githubapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapp.databinding.ActivityDetailBinding
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.databinding.ListBinding


class AdapterList(public val listUser: ArrayList<UserGithub>) : RecyclerView.Adapter<AdapterList.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterList.ListViewHolder {
        val binding = ListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterList.ListViewHolder, position: Int) {
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
                holder.binding.root.context.startActivity(intent)
            }


        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }


    class ListViewHolder(val binding: ListBinding) : RecyclerView.ViewHolder(binding.root)


}